package io.edpn.backend.trade.adapter.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommodityMarketInfoEntity {

    private ValidatedCommodityEntity validatedCommodity;
    private Double maxBuyPrice;
    private Double minBuyPrice;
    private Double avgBuyPrice;
    private Double maxSellPrice;
    private Double minSellPrice;
    private Double avgSellPrice;
    private Double minMeanPrice;
    private Double maxMeanPrice;
    private Double averageMeanPrice;
    private Long totalStock;
    private Long totalDemand;
    private Integer totalStations;
    private Integer stationsWithBuyPrice;
    private Integer stationsWithSellPrice;
    private Integer stationsWithBuyPriceLowerThanAverage;
    private Integer stationsWithSellPriceHigherThanAverage;
    private StationEntity highestSellingToStation;
    private StationEntity lowestBuyingFromStation;
}
