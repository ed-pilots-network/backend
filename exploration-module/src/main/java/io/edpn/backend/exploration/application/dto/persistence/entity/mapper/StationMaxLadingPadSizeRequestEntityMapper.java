package io.edpn.backend.exploration.application.dto.persistence.entity.mapper;

import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import io.edpn.backend.exploration.application.dto.persistence.entity.StationMaxLandingPadSizeRequestEntity;

public interface StationMaxLadingPadSizeRequestEntityMapper<T extends StationMaxLandingPadSizeRequestEntity> {
    StationMaxLandingPadSizeRequest map(StationMaxLandingPadSizeRequestEntity stationMaxLandingPadSizeRequestEntity);

    T map(StationMaxLandingPadSizeRequest stationMaxLandingPadSizeRequest);
}
