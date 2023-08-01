package io.edpn.backend.exploration.adapter.web.dto;

import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.System;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class SystemDtoMapper {

    public io.edpn.backend.exploration.application.dto.SystemDto map(System system) {
        return new SystemDto(system.getName(), coordinateFromSystem(system), system.getEliteId(), system.getStarClass());
    }

    private io.edpn.backend.exploration.application.dto.CoordinateDto coordinateFromSystem(System system) {
        if (Optional.ofNullable(system.getCoordinate()).map(Coordinate::x).isEmpty()) {
            return null;
        } else {
            return new CoordinateDto(system.getCoordinate().x(), system.getCoordinate().y(), system.getCoordinate().y());
        }
    }
}
