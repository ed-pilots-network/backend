package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationArrivalDistanceRequestEntity;
import io.edpn.backend.exploration.application.domain.StationArrivalDistanceRequest;

public class MybatisStationArrivalDistanceRequestEntityMapper {

    public StationArrivalDistanceRequest map(MybatisStationArrivalDistanceRequestEntity stationArrivalDistanceRequestEntity) {
        return new StationArrivalDistanceRequest(
                stationArrivalDistanceRequestEntity.getSystemName(),
                stationArrivalDistanceRequestEntity.getStationName(),
                stationArrivalDistanceRequestEntity.getRequestingModule()
        );
    }

    public MybatisStationArrivalDistanceRequestEntity map(StationArrivalDistanceRequest stationArrivalDistanceRequest) {
        return new MybatisStationArrivalDistanceRequestEntity(
                stationArrivalDistanceRequest.systemName(),
                stationArrivalDistanceRequest.stationName(),
                stationArrivalDistanceRequest.requestingModule()
        );
    }
}
