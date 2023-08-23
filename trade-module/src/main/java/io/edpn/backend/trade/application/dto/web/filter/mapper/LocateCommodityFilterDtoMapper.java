package io.edpn.backend.trade.application.dto.web.filter.mapper;

import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.dto.web.filter.LocateCommodityFilterDto;

public interface LocateCommodityFilterDtoMapper {
    
    LocateCommodityFilter map(LocateCommodityFilterDto locateCommodityFilterDto);
}
