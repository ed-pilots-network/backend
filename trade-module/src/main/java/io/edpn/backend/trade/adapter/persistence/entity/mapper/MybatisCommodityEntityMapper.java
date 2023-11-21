package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisCommodityEntity;
import io.edpn.backend.trade.application.domain.Commodity;
import io.edpn.backend.trade.application.dto.persistence.entity.CommodityEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.CommodityEntityMapper;

public class MybatisCommodityEntityMapper implements CommodityEntityMapper<MybatisCommodityEntity> {
    @Override
    public Commodity map(CommodityEntity commodityEntity) {
        return new Commodity(
                commodityEntity.getId(),
                commodityEntity.getName()
        );
    }
    
    @Override
    public MybatisCommodityEntity map(Commodity commodity) {
        return MybatisCommodityEntity.builder()
                .id(commodity.id())
                .name(commodity.name())
                .build();
    }
}
