package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestCoordinateDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestSystemDto;
import io.edpn.backend.trade.application.domain.Coordinate;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.object.CoordinateDto;
import io.edpn.backend.trade.application.dto.web.object.SystemDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.SystemDtoMapper;
import java.util.Optional;

public class RestSystemDtoMapper implements SystemDtoMapper {

    @Override
    public SystemDto map(System system) {
        return new RestSystemDto(
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
            return new RestCoordinateDto(system.coordinate().x(), system.coordinate().y(), system.coordinate().z());
        } else {
            return null;
        }
    }
}
