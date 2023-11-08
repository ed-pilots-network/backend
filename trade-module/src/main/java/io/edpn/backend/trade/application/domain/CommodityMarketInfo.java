package io.edpn.backend.trade.application.domain;

public record CommodityMarketInfo(
        ValidatedCommodity validatedCommodity,
        Double maxBuyPrice,
        Double minBuyPrice,
        Double avgBuyPrice,
        Double maxSellPrice,
        Double minSellPrice,
        Double avgSellPrice,
        Double minMeanPrice,
        Double maxMeanPrice,
        Double averageMeanPrice,
        Long totalStock,
        Long totalDemand,
        Integer totalStations,
        Integer stationsWithBuyPrice,
        Integer stationsWithSellPrice,
        Integer stationsWithBuyPriceLowerThanAverage,
        Integer stationsWithSellPriceHigherThanAverage,
        Station highestSellingToStation,
        Station lowestBuyingFromStation
) {
}
