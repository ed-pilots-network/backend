package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationArrivalDistanceRequestEntity;
import io.edpn.backend.exploration.application.domain.StationArrivalDistanceRequest;
import io.edpn.backend.exploration.application.dto.persistence.entity.StationArrivalDistanceRequestEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.StationArrivalDistanceRequestEntityMapper;

public class MybatisStationArrivalDistanceRequestEntityMapper implements StationArrivalDistanceRequestEntityMapper<MybatisStationArrivalDistanceRequestEntity> {
    @Override
    public StationArrivalDistanceRequest map(StationArrivalDistanceRequestEntity stationArrivalDistanceRequestEntity) {
        return new StationArrivalDistanceRequest(
                stationArrivalDistanceRequestEntity.getSystemName(),
                stationArrivalDistanceRequestEntity.getStationName(),
                stationArrivalDistanceRequestEntity.getRequestingModule()
        );
    }
    
    @Override
    public MybatisStationArrivalDistanceRequestEntity map(StationArrivalDistanceRequest stationArrivalDistanceRequest) {
        return new MybatisStationArrivalDistanceRequestEntity(
                stationArrivalDistanceRequest.systemName(),
                stationArrivalDistanceRequest.stationName(),
                stationArrivalDistanceRequest.requestingModule()
        );
    }
}
