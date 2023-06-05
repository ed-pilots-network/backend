package io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.entity;

import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.SystemEntity;
import io.edpn.backend.commodityfinder.domain.model.System;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SystemMapper {

    public System map(SystemEntity systemEntity) {
        return System.builder()
                .eliteId(systemEntity.getEliteId())
                .id(systemEntity.getId())
                .name(systemEntity.getName())
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
                .xCoordinate(system.getXCoordinate())
                .yCoordinate(system.getYCoordinate())
                .zCoordinate(system.getZCoordinate())
                .build();
    }
}
