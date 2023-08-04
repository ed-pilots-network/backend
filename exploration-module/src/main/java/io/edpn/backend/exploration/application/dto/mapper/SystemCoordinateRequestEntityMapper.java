package io.edpn.backend.exploration.application.dto.mapper;

import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.SystemCoordinateRequestEntity;

public interface SystemCoordinateRequestEntityMapper<T extends SystemCoordinateRequestEntity> {
    SystemCoordinateRequest map(SystemCoordinateRequestEntity systemCoordinateDataRequestEntity);

    T map(SystemCoordinateRequest systemCoordinateDataRequest);
}
