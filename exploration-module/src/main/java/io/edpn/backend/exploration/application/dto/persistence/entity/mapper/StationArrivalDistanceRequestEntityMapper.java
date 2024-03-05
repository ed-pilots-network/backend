package io.edpn.backend.exploration.application.dto.persistence.entity.mapper;

import io.edpn.backend.exploration.application.domain.StationArrivalDistanceRequest;
import io.edpn.backend.exploration.application.dto.persistence.entity.StationArrivalDistanceRequestEntity;

public interface StationArrivalDistanceRequestEntityMapper<T> {
    StationArrivalDistanceRequest map(StationArrivalDistanceRequestEntity stationArrivalDistanceRequestEntity);

    T map(StationArrivalDistanceRequest stationArrivalDistanceRequest);
}
