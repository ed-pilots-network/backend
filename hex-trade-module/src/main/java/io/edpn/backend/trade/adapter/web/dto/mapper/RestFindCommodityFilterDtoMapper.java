package io.edpn.backend.trade.adapter.web.dto.mapper;

import io.edpn.backend.trade.application.domain.FindCommodityFilter;
import io.edpn.backend.trade.application.dto.FindCommodityFilterDto;
import io.edpn.backend.trade.application.dto.mapper.FindCommodityFilterDtoMapper;

public class RestFindCommodityFilterDtoMapper implements FindCommodityFilterDtoMapper {
    @Override
    public FindCommodityFilter map(FindCommodityFilterDto persistenceFindCommodity) {
        return new FindCommodityFilter(
                persistenceFindCommodity.getType(),
                persistenceFindCommodity.getIsRare());
    }
}
