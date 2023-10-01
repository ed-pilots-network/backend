package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.CommodityMessage;
import io.edpn.backend.trade.application.domain.Commodity;
import io.edpn.backend.trade.application.domain.MarketDatum;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.commodity.CreateOrLoadCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.marketdatum.CreateOrUpdateOnConflictWhenNewerLatestMarketDatumPort;
import io.edpn.backend.trade.application.port.outgoing.marketdatum.CreateWhenNotExistsMarketDatumPort;
import io.edpn.backend.trade.application.port.outgoing.station.CreateOrLoadStationPort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.system.CreateOrLoadSystemPort;
import io.edpn.backend.util.CollectionUtil;
import io.edpn.backend.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class ReceiveCommodityMessageService implements ReceiveKafkaMessageUseCase<CommodityMessage.V3> {

    private final IdGenerator idGenerator;
    private final CreateOrLoadSystemPort createOrLoadSystemPort;
    private final CreateOrLoadStationPort createOrLoadStationPort;
    private final CreateOrLoadCommodityPort createOrLoadCommodityPort;
    private final CreateWhenNotExistsMarketDatumPort createWhenNotExistsMarketDatumPort;
    private final CreateOrUpdateOnConflictWhenNewerLatestMarketDatumPort createOrUpdateOnConflictWhenNewerLatestMarketDatumPort;
    private final UpdateStationPort updateStationPort;
    private final List<RequestDataUseCase<Station>> stationRequestDataServices;
    private final List<RequestDataUseCase<System>> systemRequestDataServices;

    @Override
    @Transactional
    public void receive(CommodityMessage.V3 message) {

        long start = java.lang.System.nanoTime();
        if (log.isDebugEnabled()) {
            log.debug("DefaultReceiveCommodityMessageUseCase.receive -> CommodityMessage: " + message);
        }

        var updateTimestamp = message.messageTimeStamp();

        CommodityMessage.V3.Payload payload = message.message();
        CommodityMessage.V3.Commodity[] commodities = payload.commodities();
        long marketId = payload.marketId();
        String systemName = payload.systemName();
        String stationName = payload.stationName();
        String[] prohibitedCommodities = payload.prohibited();

        CompletableFuture<Station> stationCompletableFuture = CompletableFuture.supplyAsync(() -> createOrLoadSystemPort.createOrLoad(System.builder()
                        .id(idGenerator.generateId())
                        .name(systemName)
                        .build()))
                .whenComplete((system, throwable) -> {
                    if (throwable != null) {
                        log.error("Exception occurred in retrieving system", throwable);
                    } else {
                        systemRequestDataServices.stream()
                                .filter(useCase -> useCase.isApplicable(system))
                                .forEach(useCase -> useCase.request(system));
                    }
                }).thenCompose(loadedSystem -> CompletableFuture.supplyAsync(() -> {
                    Station station = Station.builder()
                            .id(idGenerator.generateId())
                            .system(loadedSystem)
                            .name(stationName).build();
                    return createOrLoadStationPort.createOrLoad(station);
                }))
                .whenComplete((station, throwable) -> {
                    if (throwable != null) {
                        log.error("Exception occurred in retrieving station", throwable);
                    } else {
                        //if we get the message here, it always is NOT a fleet carrier
                        station.setFleetCarrier(false);

                        if (Objects.isNull(station.getMarketId())) {
                            station.setMarketId(marketId);
                        }

                        if (Objects.isNull(station.getMarketUpdatedAt()) || updateTimestamp.isAfter(station.getMarketUpdatedAt())) {
                            station.setMarketUpdatedAt(updateTimestamp);
                        }

                        stationRequestDataServices.stream()
                                .filter(useCase -> useCase.isApplicable(station))
                                .forEach(useCase -> useCase.request(station));
                    }
                });

        // get marketDataCollection
        List<CompletableFuture<MarketDatum>> completableFutureList = Arrays.stream(commodities).parallel().map(commodityFromMessage -> {
                    // get commodity
                    CompletableFuture<Commodity> commodity = CompletableFuture.supplyAsync(() -> {
                        Commodity commodity1 = Commodity.builder().id(idGenerator.generateId()).name(commodityFromMessage.name()).build();
                        return createOrLoadCommodityPort.createOrLoad(commodity1);
                    });
                    // parse market data
                    CompletableFuture<MarketDatum> marketDatum = CompletableFuture.supplyAsync(() -> getMarketDatum(commodityFromMessage, prohibitedCommodities, updateTimestamp));

                    return commodity.thenCombine(marketDatum, (c, md) -> {
                        md.setCommodity(c);
                        return md;
                    });
                })
                .toList();

        CompletableFuture<List<MarketDatum>> combinedFuture = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]))
                .thenApply(v -> completableFutureList.stream()
                        .map(CompletableFuture::join)
                        .toList());

        stationCompletableFuture.thenCombine(combinedFuture, (station, marketDatumList) -> {
            marketDatumList.parallelStream().forEach(marketDatum -> {
                createWhenNotExistsMarketDatumPort.createWhenNotExists(station.getId(), marketDatum);
                createOrUpdateOnConflictWhenNewerLatestMarketDatumPort.createOrUpdateWhenNewer(station.getId(), marketDatum);
            });
            return station;
        });

        // save station changes
        updateStationPort.update(stationCompletableFuture.join());

        if (log.isTraceEnabled()) {
            log.trace("DefaultReceiveCommodityMessageUseCase.receive -> took " + (java.lang.System.nanoTime() - start) + " nanosecond");
        }

        log.info("DefaultReceiveCommodityMessageUseCase.receive -> the message has been processed");
    }

    private MarketDatum getMarketDatum(CommodityMessage.V3.Commodity commodity, String[] prohibitedCommodities, LocalDateTime timestamp) {
        return MarketDatum.builder()
                .timestamp(timestamp)
                .meanPrice(commodity.meanPrice())
                .buyPrice(commodity.buyPrice())
                .sellPrice(commodity.sellPrice())
                .stock(commodity.stock())
                .stockBracket(commodity.stockBracket())
                .demand(commodity.demand())
                .demandBracket(commodity.demandBracket())
                .statusFlags(CollectionUtil.toList(commodity.statusFlags()))
                .prohibited(Optional.ofNullable(prohibitedCommodities).map(prohibited -> Arrays.stream(prohibited).anyMatch(prohibitedCommodity -> prohibitedCommodity.equals(commodity.name()))).orElse(false))
                .build();
    }
}
