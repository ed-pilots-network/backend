package io.edpn.backend.trade.adapter.web.dto.object;

import io.edpn.backend.trade.application.dto.web.object.CommodityMarketInfoDto;
import io.edpn.backend.trade.application.dto.web.object.StationDto;
import io.edpn.backend.trade.application.dto.web.object.ValidatedCommodityDto;

public record RestCommodityMarketInfoDto(
        ValidatedCommodityDto commodity,
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
        StationDto highestSellingToStation,
        StationDto lowestBuyingFromStation) implements CommodityMarketInfoDto {
}
