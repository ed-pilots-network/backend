package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStarEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.exploration.application.domain.Star;
import io.edpn.backend.exploration.application.dto.persistence.entity.StarEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.RingEntityMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.StarEntityMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Optional;

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
                .id(star.getId())
                .absoluteMagnitude(star.getAbsoluteMagnitude())
                .age(star.getAge())
                .arrivalDistance(star.getArrivalDistance())
                .axialTilt(star.getAxialTilt())
                .discovered(star.getDiscovered())
                .localId(star.getLocalId())
                .luminosity(star.getLuminosity())
                .mapped(star.getMapped())
                .name(star.getName())
                .radius(star.getRadius())
                .rings(Optional.ofNullable(star.getRings())
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(ringEntityMapper::map)
                        .toList())
                .rotationalPeriod(star.getRotationalPeriod())
                .starType(star.getStarType())
                .stellarMass(star.getStellarMass())
                .subclass(star.getSubclass())
                .surfaceTemperature(star.getSurfaceTemperature())
                .system(Optional.ofNullable(star.getSystem()).map(systemEntityMapper::map).orElse(null))
                .systemAddress(star.getSystemAddress())
                .horizons(star.getHorizons())
                .odyssey(star.getOdyssey())
                .estimatedScanValue(star.getEstimatedScanValue())
                .build();
    }
}
