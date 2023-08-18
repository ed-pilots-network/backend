package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisCommodityMarketInfoEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisValidatedCommodityEntity;
import io.edpn.backend.trade.application.domain.CommodityMarketInfo;
import io.edpn.backend.trade.application.dto.persistence.entity.CommodityMarketInfoEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.CommodityMarketInfoEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.ValidatedCommodityEntityMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisCommodityMarketInfoEntityMapper implements CommodityMarketInfoEntityMapper<MybatisCommodityMarketInfoEntity> {
    
    private final ValidatedCommodityEntityMapper<MybatisValidatedCommodityEntity> validatedCommodityEntityMapper;
    private final StationEntityMapper<MybatisStationEntity> stationEntityMapper;
    
    @Override
    public CommodityMarketInfo map(CommodityMarketInfoEntity commodityMarketInfoEntity) {
        return new CommodityMarketInfo(
                validatedCommodityEntityMapper.map(commodityMarketInfoEntity.getValidatedCommodity()),
                commodityMarketInfoEntity.getMaxBuyPrice(),
                commodityMarketInfoEntity.getMinBuyPrice(),
                commodityMarketInfoEntity.getAvgBuyPrice(),
                commodityMarketInfoEntity.getMaxSellPrice(),
                commodityMarketInfoEntity.getMinSellPrice(),
                commodityMarketInfoEntity.getAvgSellPrice(),
                commodityMarketInfoEntity.getMinMeanPrice(),
                commodityMarketInfoEntity.getMaxMeanPrice(),
                commodityMarketInfoEntity.getAverageMeanPrice(),
                commodityMarketInfoEntity.getTotalStock(),
                commodityMarketInfoEntity.getTotalDemand(),
                commodityMarketInfoEntity.getTotalStations(),
                commodityMarketInfoEntity.getStationsWithBuyPrice(),
                commodityMarketInfoEntity.getStationsWithSellPrice(),
                commodityMarketInfoEntity.getStationsWithBuyPriceLowerThanAverage(),
                commodityMarketInfoEntity.getStationsWithSellPriceHigherThanAverage(),
                stationEntityMapper.map(commodityMarketInfoEntity.getHighestSellingToStation()),
                stationEntityMapper.map(commodityMarketInfoEntity.getLowestBuyingFromStation())
        );
    }
    
    @Override
    public MybatisCommodityMarketInfoEntity map(CommodityMarketInfo commodityMarketInfo) {
        return MybatisCommodityMarketInfoEntity.builder()
                .validatedCommodity(validatedCommodityEntityMapper.map(commodityMarketInfo.validatedCommodity()))
                .maxBuyPrice(commodityMarketInfo.maxBuyPrice())
                .minBuyPrice(commodityMarketInfo.minBuyPrice())
                .avgBuyPrice(commodityMarketInfo.avgBuyPrice())
                .maxSellPrice(commodityMarketInfo.maxSellPrice())
                .minSellPrice(commodityMarketInfo.minSellPrice())
                .avgSellPrice(commodityMarketInfo.avgSellPrice())
                .minMeanPrice(commodityMarketInfo.minMeanPrice())
                .maxMeanPrice(commodityMarketInfo.maxMeanPrice())
                .averageMeanPrice(commodityMarketInfo.averageMeanPrice())
                .totalStock(commodityMarketInfo.totalStock())
                .totalDemand(commodityMarketInfo.totalDemand())
                .totalStations(commodityMarketInfo.totalStations())
                .stationsWithBuyPrice(commodityMarketInfo.stationsWithBuyPrice())
                .stationsWithSellPrice(commodityMarketInfo.stationsWithSellPrice())
                .stationsWithBuyPriceLowerThanAverage(commodityMarketInfo.stationsWithBuyPriceLowerThanAverage())
                .stationsWithSellPriceHigherThanAverage(commodityMarketInfo.stationsWithSellPriceHigherThanAverage())
                .highestSellingToStation(stationEntityMapper.map(commodityMarketInfo.highestSellingToStation()))
                .lowestBuyingFromStation(stationEntityMapper.map(commodityMarketInfo.lowestBuyingFromStation()))
                .build();
    }
}
