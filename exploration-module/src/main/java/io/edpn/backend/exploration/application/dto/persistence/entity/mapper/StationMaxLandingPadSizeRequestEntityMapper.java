package io.edpn.backend.exploration.application.dto.persistence.entity.mapper;

import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import io.edpn.backend.exploration.application.dto.persistence.entity.StationMaxLandingPadSizeRequestEntity;

public interface StationMaxLandingPadSizeRequestEntityMapper<T> {
    StationMaxLandingPadSizeRequest map(StationMaxLandingPadSizeRequestEntity stationMaxLandingPadSizeRequestEntity);

    T map(StationMaxLandingPadSizeRequest stationMaxLandingPadSizeRequest);
}
