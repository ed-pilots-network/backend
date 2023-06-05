package io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.entity;

import io.edpn.backend.commodityfinder.infrastructure.persistence.dto.CommodityEntity;
import io.edpn.backend.commodityfinder.domain.model.Commodity;
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
