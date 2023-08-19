package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestCommodityMarketInfoDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestStationDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestValidatedCommodityDto;
import io.edpn.backend.trade.application.domain.CommodityMarketInfo;
import io.edpn.backend.trade.application.dto.web.object.CommodityMarketInfoDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.CommodityMarketInfoDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.StationDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.ValidatedCommodityDtoMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestCommodityMarketInfoDtoMapper implements CommodityMarketInfoDtoMapper {
    
    private final ValidatedCommodityDtoMapper commodityDtoMapper;
    private final StationDtoMapper stationDtoMapper;
    
    @Override
    public CommodityMarketInfoDto map(CommodityMarketInfo commodityMarketInfo) {
        return new RestCommodityMarketInfoDto(
                (RestValidatedCommodityDto)commodityDtoMapper.map(commodityMarketInfo.validatedCommodity()),
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
                (RestStationDto)stationDtoMapper.map(commodityMarketInfo.highestSellingToStation()),
                (RestStationDto)stationDtoMapper.map(commodityMarketInfo.lowestBuyingFromStation())
        );
    }
}
