package io.edpn.backend.trade.adapter.web.dto.filter.mapper;

import io.edpn.backend.trade.adapter.web.dto.filter.RestFindCommodityFilterDto;
import io.edpn.backend.trade.application.domain.filter.FindCommodityFilter;

public class RestFindCommodityFilterDtoMapper {
    public FindCommodityFilter map(RestFindCommodityFilterDto persistenceFindCommodity) {
        return new FindCommodityFilter(
                persistenceFindCommodity.type(),
                persistenceFindCommodity.isRare());
    }
}
