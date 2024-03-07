package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.MybatisFindSystemFilter;

public class MybatisFindSystemFilterMapper {

    public MybatisFindSystemFilter map(io.edpn.backend.trade.application.domain.filter.FindSystemFilter findSystemFilter) {
        return MybatisFindSystemFilter.builder()
                .name(findSystemFilter.getName())
                .hasCoordinates(findSystemFilter.getHasCoordinates())
                .hasEliteId(findSystemFilter.getHasEliteId())
                .build();
    }
}
