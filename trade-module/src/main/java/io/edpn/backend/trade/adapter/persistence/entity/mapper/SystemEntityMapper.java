package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.SystemEntity;
import io.edpn.backend.trade.application.domain.Coordinate;
import io.edpn.backend.trade.application.domain.System;

import java.util.Optional;

public class SystemEntityMapper {

    public System map(SystemEntity systemEntity) {
        return new System(
                systemEntity.getId(),
                systemEntity.getEliteId(),
                systemEntity.getName(),
                new Coordinate(
                        systemEntity.getXCoordinate(),
                        systemEntity.getYCoordinate(),
                        systemEntity.getZCoordinate()
                )
        );
    }

    public SystemEntity map(System system) {
        return SystemEntity.builder()
                .id(system.id())
                .eliteId(system.eliteId())
                .name(system.name())
                .xCoordinate(Optional.ofNullable(system.coordinate()).map(Coordinate::x).orElse(null))
                .yCoordinate(Optional.ofNullable(system.coordinate()).map(Coordinate::y).orElse(null))
                .zCoordinate(Optional.ofNullable(system.coordinate()).map(Coordinate::z).orElse(null))
                .build();
    }
}
