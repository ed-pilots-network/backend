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
                (RestValidatedCommodityDto)commodityDtoMapper.map(commodityMarketInfo.getValidatedCommodity()),
                commodityMarketInfo.getMaxBuyPrice(),
                commodityMarketInfo.getMinBuyPrice(),
                commodityMarketInfo.getAvgBuyPrice(),
                commodityMarketInfo.getMaxSellPrice(),
                commodityMarketInfo.getMinSellPrice(),
                commodityMarketInfo.getAvgSellPrice(),
                commodityMarketInfo.getMinMeanPrice(),
                commodityMarketInfo.getMaxMeanPrice(),
                commodityMarketInfo.getAverageMeanPrice(),
                commodityMarketInfo.getTotalStock(),
                commodityMarketInfo.getTotalDemand(),
                commodityMarketInfo.getTotalStations(),
                commodityMarketInfo.getStationsWithBuyPrice(),
                commodityMarketInfo.getStationsWithSellPrice(),
                commodityMarketInfo.getStationsWithBuyPriceLowerThanAverage(),
                commodityMarketInfo.getStationsWithSellPriceHigherThanAverage(),
                (RestStationDto)stationDtoMapper.map(commodityMarketInfo.getHighestSellingToStation()),
                (RestStationDto)stationDtoMapper.map(commodityMarketInfo.getLowestBuyingFromStation())
        );
    }
}
