package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.exploration.application.domain.Ring;
import io.edpn.backend.exploration.application.dto.persistence.entity.RingEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.RingEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MybatisRingEntityMapper implements RingEntityMapper<MybatisRingEntity> {
    
    private final MybatisBodyEntityMapper bodyEntityMapper;
    private final MybatisStarEntityMapper starEntityMapper;
    
    @Override
    public Ring map(RingEntity ringEntity) {
        return new Ring(
                ringEntity.getId(),
                ringEntity.getInnerRadius(),
                ringEntity.getMass(),
                ringEntity.getName(),
                ringEntity.getOuterRadius(),
                ringEntity.getRingClass(),
                Optional.ofNullable(ringEntity.getBodyEntity()).map(bodyEntityMapper::map).orElse(null),
                Optional.ofNullable(ringEntity.getStarEntity()).map(starEntityMapper::map).orElse(null)
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
                .bodyEntity(Optional.ofNullable(ring.getBody()).map(bodyEntityMapper::map).orElse(null))
                .starEntity(Optional.ofNullable(ring.getStar()).map(starEntityMapper::map).orElse(null))
                .build();
    }
}
