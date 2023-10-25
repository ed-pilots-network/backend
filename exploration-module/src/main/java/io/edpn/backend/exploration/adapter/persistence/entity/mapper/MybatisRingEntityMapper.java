package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.exploration.application.domain.Ring;
import io.edpn.backend.exploration.application.dto.persistence.entity.RingEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.RingEntityMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisRingEntityMapper implements RingEntityMapper<MybatisRingEntity> {
    
    @Override
    public Ring map(RingEntity ringEntity) {
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
    
    @Override
    public MybatisRingEntity map(Ring ring) {
        return MybatisRingEntity.builder()
                .id(ring.getId())
                .innerRadius(ring.getInnerRadius())
                .mass(ring.getMass())
                .name(ring.getName())
                .outerRadius(ring.getOuterRadius())
                .ringClass(ring.getRingClass())
                .bodyId(ring.getBodyId())
                .starId(ring.getStarId())
                .build();
    }
}
