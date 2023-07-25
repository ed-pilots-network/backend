package io.edpn.backend.exploration.infrastructure.persistence.mappers.entity;

import io.edpn.backend.exploration.domain.model.System;
import io.edpn.backend.exploration.infrastructure.persistence.entity.SystemEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SystemMapper {

    public System map(SystemEntity systemEntity) {
        return System.builder()
                .eliteId(systemEntity.getEliteId())
                .id(systemEntity.getId())
                .name(systemEntity.getName())
                .starClass(systemEntity.getStarClass())
                .xCoordinate(systemEntity.getXCoordinate())
                .yCoordinate(systemEntity.getYCoordinate())
                .zCoordinate(systemEntity.getZCoordinate())
                .build();
    }

    public SystemEntity map(System system) {
        return SystemEntity.builder()
                .eliteId(system.getEliteId())
                .id(system.getId())
                .name(system.getName())
                .starClass(system.getStarClass())
                .xCoordinate(system.getXCoordinate())
                .yCoordinate(system.getYCoordinate())
                .zCoordinate(system.getZCoordinate())
                .build();
    }
}
