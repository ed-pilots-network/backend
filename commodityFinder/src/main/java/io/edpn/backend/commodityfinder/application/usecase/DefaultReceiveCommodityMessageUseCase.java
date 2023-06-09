package io.edpn.backend.commodityfinder.application.usecase;

import io.edpn.backend.commodityfinder.domain.model.Commodity;
import io.edpn.backend.commodityfinder.domain.model.MarketDatum;
import io.edpn.backend.commodityfinder.domain.model.Station;
import io.edpn.backend.commodityfinder.domain.model.System;
import io.edpn.backend.commodityfinder.domain.repository.CommodityRepository;
import io.edpn.backend.commodityfinder.domain.repository.StationRepository;
import io.edpn.backend.commodityfinder.domain.repository.SystemRepository;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveCommodityMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.CommodityMessage;
import io.edpn.backend.util.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class DefaultReceiveCommodityMessageUseCase implements ReceiveCommodityMessageUseCase {

    private final CommodityRepository commodityRepository;
    private final SystemRepository systemRepository;
    private final StationRepository stationRepository;

    @Override
    @Transactional
    public void receive(CommodityMessage.V3 message) {

        long start = java.lang.System.nanoTime();
        if (log.isDebugEnabled()) {
            log.debug("DefaultReceiveCommodityMessageUseCase.receive -> CommodityMessage: " + message);
        }

        var updateTimestamp = message.getMessageTimeStamp();

        // TODO check if message is newer but not in the future!

        CommodityMessage.V3.Message payload = message.getMessage();
        CommodityMessage.V3.Commodity[] commodities = payload.getCommodities();
        long marketId = payload.getMarketId();
        String systemName = payload.getSystemName();
        String stationName = payload.getStationName();
        String[] prohibitedCommodities = payload.getProhibited();


        // get system
        CompletableFuture<System> systemCompletableFuture = CompletableFuture.supplyAsync(() -> systemRepository.findOrCreateByName(systemName));

        // get station
        CompletableFuture<Station> stationCompletableFuture = CompletableFuture.supplyAsync(() -> stationRepository.findOrCreateBySystemAndStationName(systemCompletableFuture.copy().join(), stationName));
        stationCompletableFuture.whenComplete((station, throwable) -> {
            if (throwable != null) {
                log.error("Exception occurred in retrieving station", throwable);
            } else {
                if (Objects.isNull(station.getMarketId())) {
                    station.setMarketId(marketId);
                }
            }
        });

        // get marketDataCollection
        List<CompletableFuture<MarketDatum>> completableFutureList = Arrays.stream(commodities).parallel().map(commodityFromMessage -> {
                    // get commodity
                    CompletableFuture<Commodity> commodity = CompletableFuture.supplyAsync(() -> commodityRepository.findOrCreateByName(commodityFromMessage.getName()));
                    // parse market data
                    CompletableFuture<MarketDatum> marketDatum = CompletableFuture.supplyAsync(() -> getMarketDatum(commodityFromMessage, prohibitedCommodities));

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
        stationRepository.update(stationCompletableFuture.join());

        if (log.isTraceEnabled()) {
            log.trace("DefaultReceiveCommodityMessageUseCase.receive -> took " + (java.lang.System.nanoTime() - start) + " nanosecond");
        }

        log.info("DefaultReceiveCommodityMessageUseCase.receive -> the message has been processed");
    }

    private MarketDatum getMarketDatum(CommodityMessage.V3.Commodity commodity, String[] prohibitedCommodities) {
        return MarketDatum.builder()
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
