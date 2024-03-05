package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.FindCommodityFilter;

public class FindCommodityFilterMapper {

    public FindCommodityFilter map(io.edpn.backend.trade.application.domain.filter.FindCommodityFilter findCommodityFilter) {
        return FindCommodityFilter.builder()
                .type(findCommodityFilter.getType())
                .isRare(findCommodityFilter.getIsRare())
                .build();
    }
}
