package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationMaxLandingPadSizeRequestEntity;
import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisStationMaxLandingPadSizeRequestEntityMapper {

    public StationMaxLandingPadSizeRequest map(MybatisStationMaxLandingPadSizeRequestEntity stationMaxLandingPadSizeRequestEntity) {
        return new StationMaxLandingPadSizeRequest(
                stationMaxLandingPadSizeRequestEntity.getSystemName(),
                stationMaxLandingPadSizeRequestEntity.getStationName(),
                stationMaxLandingPadSizeRequestEntity.getRequestingModule());
    }

    public MybatisStationMaxLandingPadSizeRequestEntity map(StationMaxLandingPadSizeRequest stationMaxLandingPadSizeRequest) {
        return new MybatisStationMaxLandingPadSizeRequestEntity(
                stationMaxLandingPadSizeRequest.systemName(),
                stationMaxLandingPadSizeRequest.stationName(),
                stationMaxLandingPadSizeRequest.requestingModule());
    }
}
