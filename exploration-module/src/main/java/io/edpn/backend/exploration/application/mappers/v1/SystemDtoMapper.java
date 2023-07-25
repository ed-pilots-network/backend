package io.edpn.backend.exploration.application.mappers.v1;

import io.edpn.backend.exploration.application.dto.v1.CoordinateDTO;
import io.edpn.backend.exploration.application.dto.v1.SystemDTO;
import io.edpn.backend.exploration.domain.model.System;
import java.util.Objects;

;

public class SystemDtoMapper {

    public SystemDTO map(System system) {
        return SystemDTO.builder()
                .name(system.getName())
                .eliteId(system.getEliteId())
                .coordinates(coordinateFromSystem(system))
                .build();
    }

    private CoordinateDTO coordinateFromSystem(System system) {
        if (Objects.isNull(system.getXCoordinate())) {
            return null;
        } else {
            return CoordinateDTO.builder()
                    .x(system.getXCoordinate())
                    .y(system.getYCoordinate())
                    .z(system.getZCoordinate())
                    .build();
        }
    }
}
