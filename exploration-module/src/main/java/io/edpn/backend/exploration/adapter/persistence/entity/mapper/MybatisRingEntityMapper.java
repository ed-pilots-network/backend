package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.exploration.application.domain.Ring;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisRingEntityMapper {
    
    public Ring map(MybatisRingEntity ringEntity) {
        return new Ring(
                ringEntity.getId(),
                ringEntity.getInnerRadius(),
                ringEntity.getMass(),
                ringEntity.getName(),
                ringEntity.getOuterRadius(),
                ringEntity.getRingClass(),
                ringEntity.getBodyId(),
                ringEntity.getStarId());
    }

    public MybatisRingEntity map(Ring ring) {
        return MybatisRingEntity.builder()
                .id(ring.id())
                .innerRadius(ring.innerRadius())
                .mass(ring.mass())
                .name(ring.name())
                .outerRadius(ring.outerRadius())
                .ringClass(ring.ringClass())
                .bodyId(ring.bodyId())
                .starId(ring.starId())
                .build();
    }
}
