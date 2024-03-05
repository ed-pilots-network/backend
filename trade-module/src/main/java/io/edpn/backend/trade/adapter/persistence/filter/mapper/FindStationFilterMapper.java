package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.FindStationFilter;

public class FindStationFilterMapper {

    public FindStationFilter map(io.edpn.backend.trade.application.domain.filter.FindStationFilter findStationFilter) {
        return FindStationFilter.builder()
                .hasRequiredOdyssey(findStationFilter.getHasRequiredOdyssey())
                .hasLandingPadSize(findStationFilter.getHasLandingPadSize())
                .hasPlanetary(findStationFilter.getHasPlanetary())
                .hasArrivalDistance(findStationFilter.getHasArrivalDistance())
                .build();
    }
}
