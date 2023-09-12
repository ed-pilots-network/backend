package io.edpn.backend.trade.application.dto.persistence.filter.mapper;

import io.edpn.backend.trade.application.domain.filter.FindStationFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.PersistenceFindStationFilter;

public interface PersistenceFindStationFilterMapper {

    PersistenceFindStationFilter map(FindStationFilter findStationFilter);

}
