package io.edpn.backend.trade.infrastructure.persistence.mappers.entity;

import io.edpn.backend.trade.domain.model.CommodityType;
import io.edpn.backend.trade.domain.model.ValidatedCommodity;
import io.edpn.backend.trade.infrastructure.persistence.entity.ValidatedCommodityEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidatedCommodityMapper {
    
    public ValidatedCommodity map(ValidatedCommodityEntity validatedCommodityEntity){
        return ValidatedCommodity.builder()
                .id(validatedCommodityEntity.getId())
                .commodityName(validatedCommodityEntity.getCommodityName())
                .displayName(validatedCommodityEntity.getDisplayName())
                .type(CommodityType.valueOf(validatedCommodityEntity.getType()))
                .isRare(validatedCommodityEntity.getIsRare())
                .build();
    }
}
