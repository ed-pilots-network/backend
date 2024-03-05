package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.MybatisFindCommodityFilter;

public class MybatisFindCommodityFilterMapper {

    public MybatisFindCommodityFilter map(io.edpn.backend.trade.application.domain.filter.FindCommodityFilter findCommodityFilter) {
        return MybatisFindCommodityFilter.builder()
                .type(findCommodityFilter.getType())
                .isRare(findCommodityFilter.getIsRare())
                .build();
    }
}
