package io.edpn.backend.exploration.application.dto.persistence.entity.mapper;

import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.dto.persistence.entity.StationEntity;

public interface StationEntityMapper<T extends StationEntity> {
    Station map(StationEntity stationEntity);

    T map(Station station);
}
