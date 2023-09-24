package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.MybatisFindStationFilter;
import io.edpn.backend.trade.application.domain.filter.FindStationFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceFindStationFilterMapper;

public class MybatisPersistenceFindStationFilterMapper implements PersistenceFindStationFilterMapper {

    @Override
    public MybatisFindStationFilter map(FindStationFilter findStationFilter) {
        return MybatisFindStationFilter.builder()
                .hasRequiredOdyssey(findStationFilter.getHasRequiredOdyssey())
                .hasLandingPadSize(findStationFilter.getHasLandingPadSize())
                .hasPlanetary(findStationFilter.getHasPlanetary())
                .build();
    }
}
