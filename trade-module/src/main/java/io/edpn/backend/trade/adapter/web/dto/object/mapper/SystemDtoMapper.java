package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.CoordinateDto;
import io.edpn.backend.trade.adapter.web.dto.object.SystemDto;
import io.edpn.backend.trade.application.domain.Coordinate;
import io.edpn.backend.trade.application.domain.System;

import java.util.Optional;

public class SystemDtoMapper {

    public SystemDto map(System system) {
        return new SystemDto(
                system.eliteId(),
                system.name(),
                coordinateFromSystem(system)
        );
    }

    private CoordinateDto coordinateFromSystem(System system) {
        boolean isFilled = Optional.ofNullable(system.coordinate())
                .map(Coordinate::x)
                .isPresent();

        if (isFilled) {
            return new CoordinateDto(system.coordinate().x(), system.coordinate().y(), system.coordinate().z());
        } else {
            return null;
        }
    }
}
