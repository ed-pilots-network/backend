package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisValidatedCommodityEntity;
import io.edpn.backend.trade.application.domain.CommodityType;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.persistence.entity.ValidatedCommodityEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.ValidatedCommodityEntityMapper;

public class MybatisValidatedCommodityEntityMapper implements ValidatedCommodityEntityMapper<MybatisValidatedCommodityEntity> {
    @Override
    public ValidatedCommodity map(ValidatedCommodityEntity validatedCommodityEntity) {
        return new ValidatedCommodity(
                validatedCommodityEntity.getCommodityName(),
                validatedCommodityEntity.getDisplayName(),
                CommodityType.valueOf(validatedCommodityEntity.getType()),
                validatedCommodityEntity.getIsRare());
    }
    
    @Override
    public MybatisValidatedCommodityEntity map(ValidatedCommodity validatedCommodity) {
        return MybatisValidatedCommodityEntity.builder()
                .commodityName(validatedCommodity.commodityName())
                .displayName(validatedCommodity.displayName())
                .type(String.valueOf(validatedCommodity.type()))
                .isRare(validatedCommodity.isRare())
                .build();
    }
}
