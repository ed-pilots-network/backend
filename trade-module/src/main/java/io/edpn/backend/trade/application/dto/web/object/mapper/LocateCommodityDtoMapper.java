package io.edpn.backend.trade.application.dto.web.object.mapper;

import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;

public interface LocateCommodityDtoMapper {
    
    LocateCommodityDto map(LocateCommodity locateCommodity);
}
