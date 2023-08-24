package io.edpn.backend.trade.application.dto.web.filter.mapper;

import io.edpn.backend.trade.application.domain.filter.FindCommodityFilter;
import io.edpn.backend.trade.application.dto.web.filter.FindCommodityFilterDto;

public interface FindCommodityFilterDtoMapper {

    FindCommodityFilter map(FindCommodityFilterDto findCommodityFilterDto);
}
