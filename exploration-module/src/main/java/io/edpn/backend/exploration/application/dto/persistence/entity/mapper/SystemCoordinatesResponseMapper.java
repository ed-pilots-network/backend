package io.edpn.backend.exploration.application.dto.persistence.entity.mapper;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;

public interface SystemCoordinatesResponseMapper {
    SystemCoordinatesResponse map(System system);
}
