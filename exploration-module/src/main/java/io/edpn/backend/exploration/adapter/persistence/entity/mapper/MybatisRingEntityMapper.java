package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.exploration.application.domain.Ring;
import io.edpn.backend.exploration.application.dto.persistence.entity.RingEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.RingEntityMapper;

public class MybatisRingEntityMapper implements RingEntityMapper<MybatisRingEntity> {
    @Override
    public Ring map(RingEntity ringEntity) {
        return new Ring(
                ringEntity.getId(),
                ringEntity.getInnerRadius(),
                ringEntity.getMass(),
                ringEntity.getName(),
                ringEntity.getOuterRadius(),
                ringEntity.getRingClass()
        );
    }
    
    @Override
    public MybatisRingEntity map(Ring ring) {
        return MybatisRingEntity.builder()
                .id(ring.id())
                .innerRadius(ring.innerRadius())
                .mass(ring.mass())
                .name(ring.name())
                .outerRadius(ring.outerRadius())
                .ringClass(ring.ringClass())
                .build();
    }
}
