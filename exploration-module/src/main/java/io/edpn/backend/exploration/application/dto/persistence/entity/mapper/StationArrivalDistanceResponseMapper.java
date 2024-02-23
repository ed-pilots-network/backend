package io.edpn.backend.exploration.application.dto.persistence.entity.mapper;

import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationArrivalDistanceResponse;

public interface StationArrivalDistanceResponseMapper {
    StationArrivalDistanceResponse map(Station station);
}
