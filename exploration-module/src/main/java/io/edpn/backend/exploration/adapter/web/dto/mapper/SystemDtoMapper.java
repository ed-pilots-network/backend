package io.edpn.backend.exploration.adapter.web.dto.mapper;

import io.edpn.backend.exploration.adapter.web.dto.CoordinateDto;
import io.edpn.backend.exploration.adapter.web.dto.SystemDto;
import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.System;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class SystemDtoMapper implements io.edpn.backend.exploration.application.dto.mapper.SystemDtoMapper {

    public io.edpn.backend.exploration.application.dto.SystemDto map(System system) {
        return new SystemDto(system.name(), coordinateFromSystem(system), system.eliteId(), system.starClass());
    }

    private io.edpn.backend.exploration.application.dto.CoordinateDto coordinateFromSystem(System system) {
        if (Optional.ofNullable(system.coordinate()).map(Coordinate::x).isEmpty()) {
            return null;
        } else {
            return new CoordinateDto(system.coordinate().x(), system.coordinate().y(), system.coordinate().z());
        }
    }
}
