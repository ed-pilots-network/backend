package io.edpn.backend.trade.application.dto.mapper;

import io.edpn.backend.trade.application.domain.FindCommodityFilter;
import io.edpn.backend.trade.application.dto.FindCommodityFilterDto;

public interface FindCommodityFilterDtoMapper {

    FindCommodityFilter map(FindCommodityFilterDto findCommodityFilterDto);
}
