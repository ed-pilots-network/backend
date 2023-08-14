package io.edpn.backend.trade.adapter.web.dto.mapper;

import io.edpn.backend.trade.application.domain.filter.FindCommodityFilter;
import io.edpn.backend.trade.application.dto.web.filter.FindCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.filter.mapper.FindCommodityFilterDtoMapper;

public class RestFindCommodityFilterDtoMapper implements FindCommodityFilterDtoMapper {
    @Override
    public FindCommodityFilter map(FindCommodityFilterDto persistenceFindCommodity) {
        return new FindCommodityFilter(
                persistenceFindCommodity.getType(),
                persistenceFindCommodity.getIsRare());
    }
}
