package io.edpn.backend.exploration.application.dto.mapper;

import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.SystemCoordinateRequestDto;

public interface SystemCoordinateRequestEntityMapper {
    SystemCoordinateRequest map(SystemCoordinateRequestDto systemCoordinateDataRequestEntity);

    SystemCoordinateRequestDto map(SystemCoordinateRequest systemCoordinateDataRequest);
}
