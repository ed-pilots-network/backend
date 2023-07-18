package io.edpn.backend.trade.application.dto.v1;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Jacksonized
public class CommodityMarketInfoResponse {

    String commodityName;
    Double maxBuyPrice;
    Double minBuyPrice;
    Double avgBuyPrice;
    Double maxSellPrice;
    Double minSellPrice;
    Double avgSellPrice;
    Double minMeanPrice;
    Double maxMeanPrice;
    Double averageMeanPrice;
    Long totalStock;
    Long totalDemand;
    Integer totalStations;
    Integer stationsWithBuyPrice;
    Integer stationsWithSellPrice;
    Integer stationsWithBuyPriceLowerThanAverage;
    Integer stationsWithSellPriceHigherThanAverage;
    Station highestSellingToStation;
    Station lowestBuyingFromStation;

    @Value
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Jacksonized
    public static class Station {

        String name;
        Double arrivalDistance;
        System system;
    }

    @Value
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Jacksonized
    public static class System {

        String name;
        CoordinateDTO coordinates;
    }
}
