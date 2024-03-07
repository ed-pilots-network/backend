package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisCommodityEntity;
import io.edpn.backend.trade.application.domain.Commodity;

public class MybatisCommodityEntityMapper {
    public Commodity map(MybatisCommodityEntity mybatisCommodityEntity) {
        return new Commodity(
                mybatisCommodityEntity.getId(),
                mybatisCommodityEntity.getName()
        );
    }

    public MybatisCommodityEntity map(Commodity commodity) {
        return MybatisCommodityEntity.builder()
                .id(commodity.id())
                .name(commodity.name())
                .build();
    }
}
