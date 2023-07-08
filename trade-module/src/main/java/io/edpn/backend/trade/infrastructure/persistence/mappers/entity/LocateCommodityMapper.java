package io.edpn.backend.trade.infrastructure.persistence.mappers.entity;

import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.infrastructure.persistence.entity.LocateCommodityEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocateCommodityMapper {
    
    private final CommodityMapper commodityMapper;
    private final SystemMapper systemMapper;
    private final StationMapper stationMapper;
    
    public LocateCommodity map(LocateCommodityEntity locateCommodityEntity) {
        
        return LocateCommodity.builder()
                .pricesUpdatedAt(locateCommodityEntity.getPricesUpdatedAt())
                .commodity(commodityMapper.map(locateCommodityEntity.getCommodity()))
                .station(stationMapper.map(locateCommodityEntity.getStation()))
                .system(systemMapper.map(locateCommodityEntity.getSystem()))
                .supply(locateCommodityEntity.getSupply())
                .demand(locateCommodityEntity.getDemand())
                .build();
    }
}
