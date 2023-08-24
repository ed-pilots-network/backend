package io.edpn.backend.trade.application.dto.web.object.mapper;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.web.object.ValidatedCommodityDto;

public interface ValidatedCommodityDtoMapper {
    
    ValidatedCommodityDto map(ValidatedCommodity validatedCommodity);
}
