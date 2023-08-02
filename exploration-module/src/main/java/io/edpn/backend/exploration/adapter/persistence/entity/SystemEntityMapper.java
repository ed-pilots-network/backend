package io.edpn.backend.exploration.adapter.persistence.entity;

import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.System;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class SystemEntityMapper {

    public System map(SystemEntity systemEntity) {
        return System.builder()
                .eliteId(systemEntity.getEliteId())
                .id(systemEntity.getId())
                .name(systemEntity.getName())
                .starClass(systemEntity.getStarClass())
                .coordinate(from(systemEntity))
                .build();
    }

    public SystemEntity map(System system) {
        return SystemEntity.builder()
                .eliteId(system.getEliteId())
                .id(system.getId())
                .name(system.getName())
                .starClass(system.getStarClass())
                .xCoordinate(Optional.ofNullable(system.getCoordinate()).map(Coordinate::x).orElse(null))
                .yCoordinate(Optional.ofNullable(system.getCoordinate()).map(Coordinate::y).orElse(null))
                .zCoordinate(Optional.ofNullable(system.getCoordinate()).map(Coordinate::z).orElse(null))
                .build();
    }

    private Coordinate from(SystemEntity systemEntity) {
        if (Optional.ofNullable(systemEntity.getXCoordinate()).isEmpty()) {
            return null;
        } else {
            return new Coordinate(systemEntity.getXCoordinate(), systemEntity.getYCoordinate(), systemEntity.getZCoordinate());
        }
    }
}
