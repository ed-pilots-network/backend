package io.edpn.backend.exploration.application.mappers.v1;

import io.edpn.backend.exploration.application.dto.v1.CoordinateDto;
import io.edpn.backend.exploration.application.dto.v1.SystemDto;
import io.edpn.backend.exploration.domain.model.System;

import java.util.Objects;

;

public class SystemDtoMapper {

    public io.edpn.backend.exploration.domain.dto.v1.SystemDto map(System system) {
        return new SystemDto(system.getName(), coordinateFromSystem(system), system.getEliteId(), system.getStarClass());
    }

    private io.edpn.backend.exploration.domain.dto.v1.CoordinateDto coordinateFromSystem(System system) {
        if (Objects.isNull(system.getXCoordinate())) {
            return null;
        } else {
            return new CoordinateDto(system.getXCoordinate(), system.getYCoordinate(), system.getZCoordinate());
        }
    }
}
