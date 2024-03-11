package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.System;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class MybatisSystemEntityMapper {

    public System map(MybatisSystemEntity systemEntity) {
        return new System(
                systemEntity.getId(),
                systemEntity.getEliteId(),
                systemEntity.getName(),
                systemEntity.getPrimaryStarClass(),
                coordinateFromCoordinateEntity(systemEntity));
    }

    public MybatisSystemEntity map(System system) {
        return new MybatisSystemEntity(
                system.id(),
                system.name(),
                system.eliteId(),
                system.primaryStarClass(),
                Optional.ofNullable(system.coordinate()).map(Coordinate::x).orElse(null),
                Optional.ofNullable(system.coordinate()).map(Coordinate::y).orElse(null),
                Optional.ofNullable(system.coordinate()).map(Coordinate::z).orElse(null));
    }

    private Coordinate coordinateFromCoordinateEntity(MybatisSystemEntity systemEntity) {
        return new Coordinate(systemEntity.getXCoordinate(), systemEntity.getYCoordinate(), systemEntity.getZCoordinate());
    }
}