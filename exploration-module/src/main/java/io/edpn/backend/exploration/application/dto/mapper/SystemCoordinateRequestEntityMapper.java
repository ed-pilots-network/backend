package io.edpn.backend.exploration.application.dto.mapper;

import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.SystemCoordinateRequestEntity;

public interface SystemCoordinateRequestEntityMapper {
    SystemCoordinateRequest map(SystemCoordinateRequestEntity systemCoordinateDataRequestEntity);

    SystemCoordinateRequestEntity map(SystemCoordinateRequest systemCoordinateDataRequest);
}
