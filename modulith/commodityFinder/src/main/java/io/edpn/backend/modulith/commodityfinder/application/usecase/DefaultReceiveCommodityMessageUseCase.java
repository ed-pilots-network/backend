package io.edpn.backend.modulith.commodityfinder.application.usecase;

import io.edpn.backend.modulith.commodityfinder.application.service.CommodityService;
import io.edpn.backend.modulith.commodityfinder.application.service.StationService;
import io.edpn.backend.modulith.commodityfinder.application.service.SystemService;
import io.edpn.backend.modulith.commodityfinder.domain.entity.Commodity;
import io.edpn.backend.modulith.commodityfinder.domain.entity.MarketDatum;
import io.edpn.backend.modulith.commodityfinder.domain.entity.Station;
import io.edpn.backend.modulith.commodityfinder.domain.entity.System;
import io.edpn.backend.modulith.commodityfinder.domain.usecase.ReceiveCommodityMessageUseCase;
import io.edpn.backend.modulith.messageprocessorlib.application.dto.eddn.CommodityMessage;
import io.edpn.backend.modulith.util.CollectionUtil;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class DefaultReceiveCommodityMessageUseCase implements ReceiveCommodityMessageUseCase {

    private final CommodityService commodityService;
    private final SystemService systemService;
    private final StationService stationService;

    @Override
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

        // get marketDatamap
        CompletableFuture<Map<Commodity, MarketDatum>> marketDataMapCompletableFuture = CompletableFuture.supplyAsync(HashMap::new);
        Arrays.stream(commodities).parallel().map(commodityFromMessage -> {
            // get commodity
            CompletableFuture<Commodity> commodity = CompletableFuture.supplyAsync(() -> commodityService.getOrCreateByName(commodityFromMessage.getName()));
            // parse market data
            CompletableFuture<MarketDatum> marketDatum = CompletableFuture.supplyAsync(() -> getMarketDatum(commodityFromMessage, prohibitedCommodities));

            return commodity.thenCombine(marketDatum, AbstractMap.SimpleEntry::new);
        }).forEach(entryFuture -> marketDataMapCompletableFuture.thenCombine(entryFuture, (marketDataMap, entry) -> marketDataMap.put(entry.getKey(), entry.getValue())));

        // put market data map in station
        stationCompletableFuture.thenCombine(marketDataMapCompletableFuture, (station, marketDataMap) -> {
            station.setCommodityMarketData(marketDataMap);
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
