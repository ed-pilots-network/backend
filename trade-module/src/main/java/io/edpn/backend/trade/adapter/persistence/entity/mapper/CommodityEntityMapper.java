package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.CommodityEntity;
import io.edpn.backend.trade.application.domain.Commodity;

public class CommodityEntityMapper {
    public Commodity map(CommodityEntity commodityEntity) {
        return new Commodity(
                commodityEntity.getId(),
                commodityEntity.getName()
        );
    }

    public CommodityEntity map(Commodity commodity) {
        return CommodityEntity.builder()
                .id(commodity.id())
                .name(commodity.name())
                .build();
    }
}
