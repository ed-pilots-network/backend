package io.edpn.backend.modulith.commodityfinder.application.dto.persistence;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BestCommodityPriceEntity {
    private CommodityEntity commodity;
    private long maxBuyPrice;
    private long minSellPrice;
    private double averagePrice;
    private double percentStationsWithBuyPrice;
    private double percentStationsWithBuyPriceAboveAverage;
    private double percentStationsWithSellPrice;
    private double percentStationsWithSellPriceBelowAverage;
    private List<StationEntity> stationEntitiesWithLowestSellPrice;
    private List<StationEntity> stationEntitiesWithHighestBuyPrice;
}
