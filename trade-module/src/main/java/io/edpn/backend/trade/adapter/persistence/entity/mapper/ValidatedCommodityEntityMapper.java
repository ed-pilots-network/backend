package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.ValidatedCommodityEntity;
import io.edpn.backend.trade.application.domain.CommodityType;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;

public class ValidatedCommodityEntityMapper {
    public ValidatedCommodity map(ValidatedCommodityEntity validatedCommodityEntity) {
        return new ValidatedCommodity(
                validatedCommodityEntity.getId(),
                validatedCommodityEntity.getCommodityName(),
                validatedCommodityEntity.getDisplayName(),
                CommodityType.valueOf(validatedCommodityEntity.getType()),
                validatedCommodityEntity.getIsRare());
    }

    public ValidatedCommodityEntity map(ValidatedCommodity validatedCommodity) {
        return ValidatedCommodityEntity.builder()
                .id(validatedCommodity.id())
                .commodityName(validatedCommodity.commodityName())
                .displayName(validatedCommodity.displayName())
                .type(String.valueOf(validatedCommodity.type()))
                .isRare(validatedCommodity.isRare())
                .build();
    }
}
