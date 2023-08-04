package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.SystemEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class SystemEntityMapper implements io.edpn.backend.exploration.application.dto.mapper.SystemEntityMapper {

    @Override
    public System map(SystemEntity systemEntity) {
        return new System(
                systemEntity.getId(),
                systemEntity.getEliteId(),
                systemEntity.getName(),
                systemEntity.getStarClass(),
                coordinateFromCoordinateEntity(systemEntity));
    }

    @Override
    public SystemEntity map(System system) {
        return new io.edpn.backend.exploration.adapter.persistence.entity.SystemEntity(
                system.id(),
                system.name(),
                system.eliteId(),
                system.starClass(),
                Optional.ofNullable(system.coordinate()).map(Coordinate::x).orElse(null),
                Optional.ofNullable(system.coordinate()).map(Coordinate::y).orElse(null),
                Optional.ofNullable(system.coordinate()).map(Coordinate::z).orElse(null));
    }

    private Coordinate coordinateFromCoordinateEntity(SystemEntity systemEntity) {
        return new Coordinate(systemEntity.getXCoordinate(), systemEntity.getYCoordinate(), systemEntity.getZCoordinate());
    }
}