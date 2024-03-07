package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisValidatedCommodityEntity;
import io.edpn.backend.trade.application.domain.CommodityType;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;

public class MybatisValidatedCommodityEntityMapper {
    public ValidatedCommodity map(MybatisValidatedCommodityEntity mybatisValidatedCommodityEntity) {
        return new ValidatedCommodity(
                mybatisValidatedCommodityEntity.getId(),
                mybatisValidatedCommodityEntity.getCommodityName(),
                mybatisValidatedCommodityEntity.getDisplayName(),
                CommodityType.valueOf(mybatisValidatedCommodityEntity.getType()),
                mybatisValidatedCommodityEntity.getIsRare());
    }

    public MybatisValidatedCommodityEntity map(ValidatedCommodity validatedCommodity) {
        return MybatisValidatedCommodityEntity.builder()
                .id(validatedCommodity.id())
                .commodityName(validatedCommodity.commodityName())
                .displayName(validatedCommodity.displayName())
                .type(String.valueOf(validatedCommodity.type()))
                .isRare(validatedCommodity.isRare())
                .build();
    }
}
