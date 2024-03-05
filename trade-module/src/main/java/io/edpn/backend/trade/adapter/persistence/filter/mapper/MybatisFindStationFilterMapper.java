package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.MybatisFindStationFilter;

public class MybatisFindStationFilterMapper {

    public MybatisFindStationFilter map(io.edpn.backend.trade.application.domain.filter.FindStationFilter findStationFilter) {
        return MybatisFindStationFilter.builder()
                .hasRequiredOdyssey(findStationFilter.getHasRequiredOdyssey())
                .hasLandingPadSize(findStationFilter.getHasLandingPadSize())
                .hasPlanetary(findStationFilter.getHasPlanetary())
                .hasArrivalDistance(findStationFilter.getHasArrivalDistance())
                .build();
    }
}
