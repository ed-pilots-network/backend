package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.persistence.entity.SystemEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.SystemEntityMapper;

public class MybatisSystemEntityMapper implements SystemEntityMapper<MybatisSystemEntity> {
    @Override
    public System map(SystemEntity systemEntity) {
        return new System(
                systemEntity.getId(),
                systemEntity.getEliteId(),
                systemEntity.getName(),
                systemEntity.getXCoordinate(),
                systemEntity.getYCoordinate(),
                systemEntity.getZCoordinate()
        );
    }
    
    @Override
    public MybatisSystemEntity map(System system) {
        return MybatisSystemEntity.builder()
                .id(system.id())
                .eliteId(system.eliteId())
                .name(system.name())
                .xCoordinate(system.xCoordinate())
                .yCoordinate(system.yCoordinate())
                .zCoordinate(system.zCoordinate())
                .build();
    }
}
