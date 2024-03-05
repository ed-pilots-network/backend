package io.edpn.backend.trade.adapter.web.dto.filter.mapper;

import io.edpn.backend.trade.adapter.web.dto.filter.FindCommodityFilterDto;
import io.edpn.backend.trade.application.domain.filter.FindCommodityFilter;

public class FindCommodityFilterDtoMapper {
    public FindCommodityFilter map(FindCommodityFilterDto persistenceFindCommodity) {
        return new FindCommodityFilter(
                persistenceFindCommodity.type(),
                persistenceFindCommodity.isRare());
    }
}
