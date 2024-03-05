package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.CommodityMarketInfoDto;
import io.edpn.backend.trade.application.domain.CommodityMarketInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommodityMarketInfoDtoMapper {

    private final ValidatedCommodityDtoMapper commodityDtoMapper;
    private final StationDtoMapper stationDtoMapper;

    public CommodityMarketInfoDto map(CommodityMarketInfo commodityMarketInfo) {
        return new CommodityMarketInfoDto(
                commodityDtoMapper.map(commodityMarketInfo.validatedCommodity()),
                commodityMarketInfo.maxBuyPrice(),
                commodityMarketInfo.minBuyPrice(),
                commodityMarketInfo.avgBuyPrice(),
                commodityMarketInfo.maxSellPrice(),
                commodityMarketInfo.minSellPrice(),
                commodityMarketInfo.avgSellPrice(),
                commodityMarketInfo.minMeanPrice(),
                commodityMarketInfo.maxMeanPrice(),
                commodityMarketInfo.averageMeanPrice(),
                commodityMarketInfo.totalStock(),
                commodityMarketInfo.totalDemand(),
                commodityMarketInfo.totalStations(),
                commodityMarketInfo.stationsWithBuyPrice(),
                commodityMarketInfo.stationsWithSellPrice(),
                commodityMarketInfo.stationsWithBuyPriceLowerThanAverage(),
                commodityMarketInfo.stationsWithSellPriceHigherThanAverage(),
                stationDtoMapper.map(commodityMarketInfo.highestSellingToStation()),
                stationDtoMapper.map(commodityMarketInfo.lowestBuyingFromStation())
        );
    }
}
