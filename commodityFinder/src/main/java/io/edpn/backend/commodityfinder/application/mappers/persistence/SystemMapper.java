package io.edpn.backend.commodityfinder.application.mappers.persistence;

import io.edpn.backend.commodityfinder.application.dto.persistence.SystemEntity;
import io.edpn.backend.commodityfinder.domain.entity.System;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
