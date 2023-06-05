package io.edpn.backend.commodityfinder.domain.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BestCommodityPrice {
    private Commodity commodity;
    private long maxBuyPrice;
    private long minSellPrice;
    private double averagePrice;
    private double percentStationsWithBuyPrice;
    private double percentStationsWithBuyPriceAboveAverage;
    private double percentStationsWithSellPrice;
    private double percentStationsWithSellPriceBelowAverage;
    private List<Station> stationEntitiesWithLowestSellPrice;
    private List<Station> stationEntitiesWithHighestBuyPrice;
}

