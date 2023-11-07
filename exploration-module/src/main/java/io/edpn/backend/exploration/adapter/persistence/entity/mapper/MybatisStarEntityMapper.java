package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStarEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.exploration.application.domain.Star;
import io.edpn.backend.exploration.application.dto.persistence.entity.StarEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.RingEntityMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.StarEntityMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEntityMapper;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisStarEntityMapper implements StarEntityMapper<MybatisStarEntity> {
    
    private final RingEntityMapper<MybatisRingEntity> ringEntityMapper;
    private final SystemEntityMapper<MybatisSystemEntity> systemEntityMapper;
    
    @Override
    public Star map(StarEntity starEntity) {
        return Star.builder()
                .id(starEntity.getId())
                .absoluteMagnitude(starEntity.getAbsoluteMagnitude())
                .age(starEntity.getAge())
                .arrivalDistance(starEntity.getArrivalDistance())
                .axialTilt(starEntity.getAxialTilt())
                .discovered(starEntity.getDiscovered())
                .localId(starEntity.getLocalId())
                .luminosity(starEntity.getLuminosity())
                .mapped(starEntity.getMapped())
                .name(starEntity.getName())
                .radius(starEntity.getRadius())
                .rings(Optional.ofNullable(starEntity.getRings())
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(ringEntityMapper::map)
                        .toList())
                .rotationalPeriod(starEntity.getRotationalPeriod())
                .starType(starEntity.getStarType())
                .stellarMass(starEntity.getStellarMass())
                .subclass(starEntity.getSubclass())
                .surfaceTemperature(starEntity.getSurfaceTemperature())
                .system(Optional.ofNullable(starEntity.getSystem()).map(systemEntityMapper::map).orElse(null))
                .systemAddress(starEntity.getSystemAddress())
                .horizons(starEntity.getHorizons())
                .odyssey(starEntity.getOdyssey())
                .estimatedScanValue(starEntity.getEstimatedScanValue())
                .build();
    }
    
    @Override
    public MybatisStarEntity map(Star star) {
        return MybatisStarEntity.builder()
                .id(star.id())
                .absoluteMagnitude(star.absoluteMagnitude())
                .age(star.age())
                .arrivalDistance(star.arrivalDistance())
                .axialTilt(star.axialTilt())
                .discovered(star.discovered())
                .localId(star.localId())
                .luminosity(star.luminosity())
                .mapped(star.mapped())
                .name(star.name())
                .radius(star.radius())
                .rings(Optional.ofNullable(star.rings())
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(ringEntityMapper::map)
                        .toList())
                .rotationalPeriod(star.rotationalPeriod())
                .starType(star.starType())
                .stellarMass(star.stellarMass())
                .subclass(star.subclass())
                .surfaceTemperature(star.surfaceTemperature())
                .system(Optional.ofNullable(star.system()).map(systemEntityMapper::map).orElse(null))
                .systemAddress(star.systemAddress())
                .horizons(star.horizons())
                .odyssey(star.odyssey())
                .estimatedScanValue(star.estimatedScanValue())
                .build();
    }
}
