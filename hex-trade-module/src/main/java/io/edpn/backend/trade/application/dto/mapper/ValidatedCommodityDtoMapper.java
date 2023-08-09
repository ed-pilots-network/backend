package io.edpn.backend.trade.application.dto.mapper;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.ValidatedCommodityDto;

public interface ValidatedCommodityDtoMapper {
    
    ValidatedCommodityDto map(ValidatedCommodity validatedCommodity);
}
