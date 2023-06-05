package io.edpn.backend.commodityfinder.application.dto.rest;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
@Builder
public class BestCommodityPriceResponse {

    String commodityName;
    double buyPrice;
    double sellPrice;
    double averagePrice;
    double stationsThatBuy;
    double stationsThatBuyAboveAverage;
    double stationsThatSell;
    double stationsThatSellBelowAverage;
    List<Station> lowestSellingStation;
    List<Station> highestStation;

    @Value(staticConstructor = "of")
    @Builder
    public static class Station {

        String name;
        double arrivalDistance;
        System system;
    }

    @Value(staticConstructor = "of")
    @Builder
    public static class System {

        String name;
        double xCoordinate;
        double yCoordinate;
        double zCoordinate;
    }
}
