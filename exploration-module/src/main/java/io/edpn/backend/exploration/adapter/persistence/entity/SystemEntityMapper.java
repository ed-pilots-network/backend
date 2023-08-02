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
        return System.builder()
                .eliteId(systemEntity.eliteId())
                .id(systemEntity.id())
                .name(systemEntity.name())
                .starClass(systemEntity.starClass())
                .coordinate(coordinateFromCoordinateEntity(systemEntity.coordinates()))
                .build();
    }

    @Override
    public SystemEntity map(System system) {
        return new SystemEntity(
                system.getId(),
                system.getName(),
                coordinateEntityFromCoordinate(system.getCoordinate()),
                system.getEliteId(),
                system.getStarClass());
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
