package io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.entity;

import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.CommodityMarketInfoEntity;
import io.edpn.backend.commodityfinder.domain.model.CommodityMarketInfo;
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
                .minSellPrice(entity.getMinSellPrice())
                .averagePrice(entity.getAveragePrice())
                .percentStationsWithBuyPrice(entity.getPercentStationsWithBuyPrice())
                .percentStationsWithBuyPriceAboveAverage(entity.getPercentStationsWithBuyPriceAboveAverage())
                .percentStationsWithSellPrice(entity.getPercentStationsWithSellPrice())
                .percentStationsWithSellPriceBelowAverage(entity.getPercentStationsWithSellPriceBelowAverage())
                .stationEntitiesWithLowestSellPrice(entity.getStationEntitiesWithLowestSellPrice().stream().map(stationMapper::map).collect(Collectors.toCollection(LinkedList::new)))
                .stationEntitiesWithHighestBuyPrice(entity.getStationEntitiesWithHighestBuyPrice().stream().map(stationMapper::map).collect(Collectors.toCollection(LinkedList::new)))
                .build();
    }
}
