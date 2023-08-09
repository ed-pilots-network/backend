package io.edpn.backend.trade.application.dto.mapper;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.ValidatedCommodityEntity;

public interface ValidatedCommodityEntityMapper<T extends ValidatedCommodityEntity> {
    
    ValidatedCommodity map(ValidatedCommodityEntity validatedCommodityEntity);
    
    T map(ValidatedCommodity validatedCommodity);
}
