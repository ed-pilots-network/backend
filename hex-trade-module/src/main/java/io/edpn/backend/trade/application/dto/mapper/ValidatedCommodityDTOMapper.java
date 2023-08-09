package io.edpn.backend.trade.application.dto.mapper;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.ValidatedCommodityDTO;

public interface ValidatedCommodityDTOMapper {
    
    ValidatedCommodityDTO map(ValidatedCommodity validatedCommodity);
}
