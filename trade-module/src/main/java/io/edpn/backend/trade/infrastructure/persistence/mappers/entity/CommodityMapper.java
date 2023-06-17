package io.edpn.backend.trade.infrastructure.persistence.mappers.entity;

import io.edpn.backend.trade.infrastructure.persistence.entity.CommodityEntity;
import io.edpn.backend.trade.domain.model.Commodity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommodityMapper {

    public Commodity map(CommodityEntity commodityEntity) {
        return Commodity.builder()
                .id(commodityEntity.getId())
                .name(commodityEntity.getName())
                .build();
    }

    public CommodityEntity map(Commodity commodity) {
        return CommodityEntity.builder()
                .id(commodity.getId())
                .name(commodity.getName())
                .build();
    }
}
