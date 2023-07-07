package io.edpn.backend.trade.infrastructure.persistence.mappers.entity;

import io.edpn.backend.trade.domain.model.FindCommodity;
import io.edpn.backend.trade.infrastructure.persistence.entity.FindCommodityEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindCommodityMapper {
    
    private final CommodityMapper commodityMapper;
    private final SystemMapper systemMapper;
    private final StationMapper stationMapper;
    
    public FindCommodity map(FindCommodityEntity findCommodityEntity) {
        return FindCommodity.builder()
                .pricesUpdatedAt(findCommodityEntity.getPricesUpdatedAt())
                .commodity(commodityMapper.map(findCommodityEntity.getCommodity()))
                .station(stationMapper.map(findCommodityEntity.getStation()))
                .system(systemMapper.map(findCommodityEntity.getSystem()))
                .build();
    }
}
