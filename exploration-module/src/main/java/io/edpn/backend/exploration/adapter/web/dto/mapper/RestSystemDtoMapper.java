package io.edpn.backend.exploration.adapter.web.dto.mapper;

import io.edpn.backend.exploration.adapter.web.dto.RestCoordinateDto;
import io.edpn.backend.exploration.adapter.web.dto.RestSystemDto;
import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.System;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class RestSystemDtoMapper {

    public RestSystemDto map(System system) {
        return new RestSystemDto(system.name(), coordinateFromSystem(system), system.eliteId(), system.primaryStarClass());
    }

    private RestCoordinateDto coordinateFromSystem(System system) {
        boolean isFilled = Optional.ofNullable(system.coordinate())
                .map(Coordinate::x)
                .isPresent();

        if (isFilled) {
            return new RestCoordinateDto(system.coordinate().x(), system.coordinate().y(), system.coordinate().z());
        } else {
            return null;
        }
    }
}
