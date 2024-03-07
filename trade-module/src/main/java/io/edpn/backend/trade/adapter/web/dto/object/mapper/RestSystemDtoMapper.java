package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestCoordinateDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestSystemDto;
import io.edpn.backend.trade.application.domain.Coordinate;
import io.edpn.backend.trade.application.domain.System;

import java.util.Optional;

public class RestSystemDtoMapper {

    public RestSystemDto map(System system) {
        return new RestSystemDto(
                system.eliteId(),
                system.name(),
                coordinateFromSystem(system)
        );
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
