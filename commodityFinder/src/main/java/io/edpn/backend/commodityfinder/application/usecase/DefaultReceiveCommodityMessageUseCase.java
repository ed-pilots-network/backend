package io.edpn.backend.commodityfinder.application.usecase;

import io.edpn.backend.commodityfinder.application.service.CommodityService;
import io.edpn.backend.commodityfinder.application.service.StationService;
import io.edpn.backend.commodityfinder.application.service.SystemService;
import io.edpn.backend.commodityfinder.domain.entity.Commodity;
import io.edpn.backend.commodityfinder.domain.entity.MarketDatum;
import io.edpn.backend.commodityfinder.domain.entity.Station;
import io.edpn.backend.commodityfinder.domain.entity.System;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveCommodityMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.CommodityMessage;
import io.edpn.backend.util.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class DefaultReceiveCommodityMessageUseCase implements ReceiveCommodityMessageUseCase {

    private final CommodityService commodityService;
    private final SystemService systemService;
    private final StationService stationService;

    @Override
    @Transactional
    public void receive(CommodityMessage.V3 message) {

        long start = java.lang.System.nanoTime();
        if (log.isDebugEnabled()) {
            log.debug("DefaultReceiveCommodityMessageUseCase.receive -> commodityMessage: " + message);
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
        CompletableFuture<System> systemCompletableFuture = CompletableFuture.supplyAsync(() -> systemService.getOrCreateByName(systemName));

        // get station
        CompletableFuture<Station> stationCompletableFuture = CompletableFuture.supplyAsync(() -> stationService.getOrCreateBySystemAndStationName(systemCompletableFuture.copy().join(), stationName));
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
                    CompletableFuture<Commodity> commodity = CompletableFuture.supplyAsync(() -> commodityService.getOrCreateByName(commodityFromMessage.getName()));
                    // parse market data
                    CompletableFuture<MarketDatum> marketDatum = CompletableFuture.supplyAsync(() -> getMarketDatum(commodityFromMessage, prohibitedCommodities));

                    return commodity.thenCombine(marketDatum, (c, md) -> {
                        md.setCommodity(c);
                        return md;
                    });
                })
                .toList();

        CompletableFuture<List<MarketDatum>> combinedFuture = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]))
                .thenApply(v -> completableFutureList.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));


        // put market data map in station
        stationCompletableFuture.thenCombine(combinedFuture, (station, marketDataMap) -> {
            station.setMarketData(marketDataMap);
            return station;
        });

        // save station
        stationService.save(stationCompletableFuture.join());

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
