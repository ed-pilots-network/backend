package io.edpn.backend.trade.infrastructure.persistence.mappers.entity;

import io.edpn.backend.trade.domain.model.CommodityMarketInfo;
import io.edpn.backend.trade.infrastructure.persistence.entity.CommodityMarketInfoEntity;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CommodityMarketInfoMapper {

    private final CommodityMapper commodityMapper;
    private final StationMapper stationMapper;

    public List<CommodityMarketInfo> map(List<CommodityMarketInfoEntity> entities) {
        return entities.stream()
                .map(this::map)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public CommodityMarketInfo map(CommodityMarketInfoEntity entity) {
        return CommodityMarketInfo.builder()
                .commodity(commodityMapper.map(entity.getCommodity()))
                .maxBuyPrice(entity.getMaxBuyPrice())
                .minBuyPrice(entity.getMinBuyPrice())
                .avgBuyPrice(entity.getAvgBuyPrice())
                .maxSellPrice(entity.getMaxSellPrice())
                .minSellPrice(entity.getMinSellPrice())
                .avgSellPrice(entity.getAvgSellPrice())
                .minMeanPrice(entity.getMinMeanPrice())
                .maxMeanPrice(entity.getMaxMeanPrice())
                .averageMeanPrice(entity.getAverageMeanPrice())
                .totalStock(entity.getTotalStock())
                .totalDemand(entity.getTotalDemand())
                .totalStations(entity.getTotalStations())
                .stationsWithBuyPrice(entity.getStationsWithBuyPrice())
                .stationsWithSellPrice(entity.getStationsWithSellPrice())
                .stationsWithBuyPriceLowerThanAverage(entity.getStationsWithBuyPriceLowerThanAverage())
                .stationsWithSellPriceHigherThanAverage(entity.getStationsWithSellPriceHigherThanAverage())
                .highestSellingToStation(stationMapper.map(entity.getHighestSellingToStation()))
                .lowestBuyingFromStation(stationMapper.map(entity.getLowestBuyingFromStation()))
                .build();
    }
}
