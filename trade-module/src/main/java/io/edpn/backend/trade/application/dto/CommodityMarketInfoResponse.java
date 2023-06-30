package io.edpn.backend.trade.application.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
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
    Integer totalStock;
    Integer totalDemand;
    Integer totalStations;
    Integer stationsWithBuyPrice;
    Integer stationsWithSellPrice;
    Integer stationsWithBuyPriceLowerThanAverage;
    Integer stationsWithSellPriceHigherThanAverage;
    Station highestSellingToStation;
    Station lowestBuyingFromStation;

    @Value
    @Builder
    public static class Station {

        String name;
        Double arrivalDistance;
        System system;
    }

    @Value
    @Builder
    public static class System {

        String name;
        Double xCoordinate;
        Double yCoordinate;
        Double zCoordinate;
    }
}
