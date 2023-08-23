package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.MybatisFindSystemFilter;
import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceFindSystemFilterMapper;

public class MybatisPersistenceFindSystemFilterMapper implements PersistenceFindSystemFilterMapper {

    @Override
    public MybatisFindSystemFilter map(FindSystemFilter findSystemFilter) {
        return MybatisFindSystemFilter.builder()
                .name(findSystemFilter.getName())
                .hasCoordinates(findSystemFilter.getHasCoordinates())
                .hasEliteId(findSystemFilter.getHasEliteId())
                .build();
    }
}
