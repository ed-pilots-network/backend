package io.edpn.backend.exploration.adapter.persistence.entity;

import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.CoordinateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class SystemEntityMapper implements io.edpn.backend.exploration.application.dto.mapper.SystemEntityMapper {

    @Override
    public System map(SystemEntity systemEntity) {
        return new System(
                systemEntity.id(),
                systemEntity.eliteId(),
                systemEntity.name(),
                systemEntity.starClass(),
                coordinateFromCoordinateEntity(systemEntity.coordinate()));
    }

    @Override
    public SystemEntity map(System system) {
        return new SystemEntity(
                system.id(),
                system.name(),
                coordinateEntityFromCoordinate(system.coordinate()),
                system.eliteId(),
                system.starClass());
    }

    private Coordinate coordinateFromCoordinateEntity(CoordinateEntity coordinateEntity) {
        if (Optional.ofNullable(coordinateEntity).map(CoordinateDto::x).isEmpty()) {
            return null;
        } else {
            return new Coordinate(coordinateEntity.x(), coordinateEntity.y(), coordinateEntity.y());
        }
    }

    private CoordinateEntity coordinateEntityFromCoordinate(Coordinate coordinate) {
        if (Optional.ofNullable(coordinate).map(Coordinate::x).isEmpty()) {
            return null;
        } else {
            return new CoordinateEntity(coordinate.x(), coordinate.y(), coordinate.y());
        }
    }
}
