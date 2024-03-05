package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationMaxLandingPadSizeRequestEntity;
import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import io.edpn.backend.exploration.application.dto.persistence.entity.StationMaxLandingPadSizeRequestEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.StationMaxLandingPadSizeRequestEntityMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisStationMaxLandingPadSizeRequestEntityMapper implements StationMaxLandingPadSizeRequestEntityMapper<MybatisStationMaxLandingPadSizeRequestEntity> {

    @Override
    public StationMaxLandingPadSizeRequest map(StationMaxLandingPadSizeRequestEntity stationMaxLandingPadSizeRequestEntity) {
        return new StationMaxLandingPadSizeRequest(
                stationMaxLandingPadSizeRequestEntity.getSystemName(),
                stationMaxLandingPadSizeRequestEntity.getStationName(),
                stationMaxLandingPadSizeRequestEntity.getRequestingModule());
    }

    @Override
    public MybatisStationMaxLandingPadSizeRequestEntity map(StationMaxLandingPadSizeRequest stationMaxLandingPadSizeRequest) {
        return new MybatisStationMaxLandingPadSizeRequestEntity(
                stationMaxLandingPadSizeRequest.systemName(),
                stationMaxLandingPadSizeRequest.stationName(),
                stationMaxLandingPadSizeRequest.requestingModule());
    }
}
