package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.exploration.application.domain.Ring;
import io.edpn.backend.exploration.application.dto.persistence.entity.RingEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.RingEntityMapper;

import java.util.Optional;

public class MybatisRingEntityMapper implements RingEntityMapper<MybatisRingEntity> {
    
    MybatisBodyEntityMapper mybatisBodyEntityMapper;
    
    @Override
    public Ring map(RingEntity ringEntity) {
        return new Ring(
                ringEntity.getId(),
                ringEntity.getInnerRadius(),
                ringEntity.getMass(),
                ringEntity.getName(),
                ringEntity.getOuterRadius(),
                ringEntity.getRingClass(),
                Optional.ofNullable(ringEntity.getBodyEntity()).map(mybatisBodyEntityMapper::map).orElse(null),
                null//TODO;
        );
    }
    
    @Override
    public MybatisRingEntity map(Ring ring) {
        return MybatisRingEntity.builder()
                .id(ring.getId())
                .innerRadius(ring.getInnerRadius())
                .mass(ring.getMass())
                .name(ring.getName())
                .outerRadius(ring.getOuterRadius())
                .ringClass(ring.getRingClass())
                .bodyEntity(Optional.of(ring.getBody()).map(mybatisBodyEntityMapper::map).orElse(null))
                .build();
    }
}
