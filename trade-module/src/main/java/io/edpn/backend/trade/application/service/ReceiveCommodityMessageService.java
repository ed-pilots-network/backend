package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.CommodityMessage;
import io.edpn.backend.trade.application.domain.Commodity;
import io.edpn.backend.trade.application.domain.MarketDatum;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.commodity.LoadOrCreateCommodityByNamePort;
import io.edpn.backend.trade.application.port.outgoing.marketdatum.ExistsByStationNameAndSystemNameAndTimestampPort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadOrCreateBySystemAndStationNamePort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
import io.edpn.backend.util.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class ReceiveCommodityMessageService implements ReceiveKafkaMessageUseCase<CommodityMessage.V3> {
    
    private final ExistsByStationNameAndSystemNameAndTimestampPort existsByStationNameAndSystemNameAndTimestamp;
    private final LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;
    private final LoadOrCreateBySystemAndStationNamePort loadOrCreateBySystemAndStationNamePort;
    private final LoadOrCreateCommodityByNamePort loadOrCreateCommodityByNamePort;
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

        var updateTimestamp = message.getMessageTimeStamp();

        CommodityMessage.V3.Message payload = message.getMessage();
        CommodityMessage.V3.Commodity[] commodities = payload.getCommodities();
        long marketId = payload.getMarketId();
        String systemName = payload.getSystemName();
        String stationName = payload.getStationName();
        String[] prohibitedCommodities = payload.getProhibited();

        //do we have this data already?
        if (existsByStationNameAndSystemNameAndTimestamp.exists(systemName, stationName, updateTimestamp)) {
            log.debug("data with the same key as received message already present in database. SKIPPING");
            return;
        }


        // get system
        CompletableFuture<System> systemCompletableFuture = CompletableFuture.supplyAsync(() -> loadOrCreateSystemByNamePort.loadOrCreateSystemByName(systemName))
                .whenComplete((system, throwable) -> {
                    if (throwable != null) {
                        log.error("Exception occurred in retrieving system", throwable);
                    } else {
                        systemRequestDataServices.stream()
                                .filter(useCase -> useCase.isApplicable(system))
                                .forEach(useCase -> useCase.request(system));
                    }
                });

        // get station
        CompletableFuture<Station> stationCompletableFuture = CompletableFuture.supplyAsync(() -> loadOrCreateBySystemAndStationNamePort.loadOrCreateBySystemAndStationName(systemCompletableFuture.copy().join(), stationName));
        stationCompletableFuture.whenComplete((station, throwable) -> {
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
                    CompletableFuture<Commodity> commodity = CompletableFuture.supplyAsync(() -> loadOrCreateCommodityByNamePort.loadOrCreate(commodityFromMessage.getName()));
                    // parse market data
                    CompletableFuture<MarketDatum> marketDatum = CompletableFuture.supplyAsync(() -> getMarketDatum(commodityFromMessage, prohibitedCommodities, updateTimestamp));

                    return commodity.thenCombine(marketDatum, (c, md) -> {
                        md.setCommodity(c);
                        return md;
                    });
                })
                .collect(Collectors.toCollection(LinkedList::new));

        CompletableFuture<List<MarketDatum>> combinedFuture = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]))
                .thenApply(v -> completableFutureList.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toCollection(LinkedList::new)));


        // put market data map in station
        stationCompletableFuture.thenCombine(combinedFuture, (station, marketDataMap) -> {
            station.setMarketData(marketDataMap);
            return station;
        });

        // save station
        updateStationPort.update(stationCompletableFuture.join());

        if (log.isTraceEnabled()) {
            log.trace("DefaultReceiveCommodityMessageUseCase.receive -> took " + (java.lang.System.nanoTime() - start) + " nanosecond");
        }

        log.info("DefaultReceiveCommodityMessageUseCase.receive -> the message has been processed");
    }

    private MarketDatum getMarketDatum(CommodityMessage.V3.Commodity commodity, String[] prohibitedCommodities, LocalDateTime timestamp) {
        return MarketDatum.builder()
                .timestamp(timestamp)
                .meanPrice(commodity.getMeanPrice())
                .buyPrice(commodity.getBuyPrice())
                .sellPrice(commodity.getSellPrice())
                .stock(commodity.getStock())
                .stockBracket(commodity.getStockBracket())
                .demand(commodity.getDemand())
                .demandBracket(commodity.getDemandBracket())
                .statusFlags(CollectionUtil.toList(commodity.getStatusFlags()))
                .prohibited(Arrays.stream(prohibitedCommodities).anyMatch(prohibitedCommodity -> prohibitedCommodity.equals(commodity.getName())))
                .build();
    }
}
