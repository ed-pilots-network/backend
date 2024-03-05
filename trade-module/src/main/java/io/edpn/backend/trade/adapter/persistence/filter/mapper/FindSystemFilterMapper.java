package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.FindSystemFilter;

public class FindSystemFilterMapper {

    public FindSystemFilter map(io.edpn.backend.trade.application.domain.filter.FindSystemFilter findSystemFilter) {
        return FindSystemFilter.builder()
                .name(findSystemFilter.getName())
                .hasCoordinates(findSystemFilter.getHasCoordinates())
                .hasEliteId(findSystemFilter.getHasEliteId())
                .build();
    }
}
