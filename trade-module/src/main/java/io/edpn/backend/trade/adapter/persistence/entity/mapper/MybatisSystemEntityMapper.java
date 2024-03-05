package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.trade.application.domain.Coordinate;
import io.edpn.backend.trade.application.domain.System;

import java.util.Optional;

public class MybatisSystemEntityMapper {

    public System map(MybatisSystemEntity mybatisSystemEntity) {
        return new System(
                mybatisSystemEntity.getId(),
                mybatisSystemEntity.getEliteId(),
                mybatisSystemEntity.getName(),
                new Coordinate(
                        mybatisSystemEntity.getXCoordinate(),
                        mybatisSystemEntity.getYCoordinate(),
                        mybatisSystemEntity.getZCoordinate()
                )
        );
    }

    public MybatisSystemEntity map(System system) {
        return MybatisSystemEntity.builder()
                .id(system.id())
                .eliteId(system.eliteId())
                .name(system.name())
                .xCoordinate(Optional.ofNullable(system.coordinate()).map(Coordinate::x).orElse(null))
                .yCoordinate(Optional.ofNullable(system.coordinate()).map(Coordinate::y).orElse(null))
                .zCoordinate(Optional.ofNullable(system.coordinate()).map(Coordinate::z).orElse(null))
                .build();
    }
}
