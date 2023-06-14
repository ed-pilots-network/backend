package io.edpn.backend.commodityfinder.application.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value(staticConstructor = "of")
@Builder
public class CommodityMarketInfoResponse {

    private String commodityName;
    private Double maxBuyPrice;
    private Double minBuyPrice;
    private Double avgBuyPrice;
    private Double maxSellPrice;
    private Double minSellPrice;
    private Double avgSellPrice;
    private Double minMeanPrice;
    private Double maxMeanPrice;
    private Double averageMeanPrice;
    private Integer totalStock;
    private Integer totalDemand;
    private Integer totalStations;
    private Integer stationsWithBuyPrice;
    private Integer stationsWithSellPrice;
    private Integer stationsWithBuyPriceLowerThanAverage;
    private Integer stationsWithSellPriceHigherThanAverage;
    private Station highestSellingToStation;
    private Station lowestBuyingFromStation;

    @Value(staticConstructor = "of")
    @Builder
    public static class Station {

        String name;
        Double arrivalDistance;
        System system;
    }

    @Value(staticConstructor = "of")
    @Builder
    public static class System {

        String name;
        Double xCoordinate;
        Double yCoordinate;
        Double zCoordinate;
    }
}
