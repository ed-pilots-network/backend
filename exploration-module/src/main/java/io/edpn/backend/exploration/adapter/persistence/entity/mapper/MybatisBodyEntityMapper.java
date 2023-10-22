package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisBodyEntity;
import io.edpn.backend.exploration.application.domain.Body;
import io.edpn.backend.exploration.application.dto.persistence.entity.BodyEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.BodyEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
public class MybatisBodyEntityMapper implements BodyEntityMapper<MybatisBodyEntity> {
    
    MybatisRingEntityMapper ringEntityMapper;
    MybatisSystemEntityMapper systemEntityMapper;
    
    @Override
    public Body map(BodyEntity bodyEntity) {
        return new Body(
                bodyEntity.getId(),
                bodyEntity.getArrivalDistance(),
                bodyEntity.getAscendingNode(),
                bodyEntity.getAtmosphere(),
                bodyEntity.getAtmosphericComposition(),
                bodyEntity.getAxialTilt(),
                bodyEntity.getBodyComposition(),
                bodyEntity.getDiscovered(),
                bodyEntity.getMapped(),
                bodyEntity.getName(),
                bodyEntity.getLocalId(),
                bodyEntity.getEccentricity(),
                bodyEntity.getLandable(),
                bodyEntity.getMass(),
                bodyEntity.getMaterials(),
                bodyEntity.getMeanAnomaly(),
                bodyEntity.getOrbitalInclination(),
                bodyEntity.getOrbitalPeriod(),
                bodyEntity.getParents(),
                bodyEntity.getPeriapsis(),
                bodyEntity.getPlanetClass(),
                bodyEntity.getRadius(),
                Optional.ofNullable(bodyEntity.getRings())
                        .orElse(new ArrayList<>())
                        .stream().map(ring -> ringEntityMapper.map(ring))
                        .toList(),
                bodyEntity.getRotationPeriod(),
                bodyEntity.getSemiMajorAxis(),
                bodyEntity.getSurfaceGravity(),
                bodyEntity.getSurfacePressure(),
                bodyEntity.getSurfaceTemperature(),
                Optional.ofNullable(bodyEntity.getSystem())
                        .map(systemEntity -> systemEntityMapper.map(systemEntity))
                        .orElse(null),
                bodyEntity.getSystemAddress(),
                bodyEntity.getTerraformState(),
                bodyEntity.getTidalLock(),
                bodyEntity.getVolcanism(),
                bodyEntity.getHorizons(),
                bodyEntity.getOdyssey(),
                bodyEntity.getEstimatedScanValue()
        );
    }
    
    @Override
    public MybatisBodyEntity map(Body body) {
        return MybatisBodyEntity.builder()
                .id(body.getId())
                .arrivalDistance(body.getArrivalDistance())
                .ascendingNode(body.getAscendingNode())
                .atmosphere(body.getAtmosphere())
                .atmosphericComposition(body.getAtmosphericComposition())
                .axialTilt(body.getAxialTilt())
                .bodyComposition(body.getBodyComposition())
                .discovered(body.getDiscovered())
                .mapped(body.getMapped())
                .name(body.getName())
                .localId(body.getLocalId())
                .eccentricity(body.getEccentricity())
                .landable(body.getLandable())
                .mass(body.getMass())
                .materials(body.getMaterials())
                .meanAnomaly(body.getMeanAnomaly())
                .orbitalInclination(body.getOrbitalInclination())
                .orbitalPeriod(body.getOrbitalPeriod())
                .parents(body.getParents())
                .periapsis(body.getPeriapsis())
                .planetClass(body.getPlanetClass())
                .radius(body.getRadius())
                .rings(Optional.ofNullable(body.getRings())
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(ring -> ringEntityMapper.map(ring))
                        .toList())
                .rotationPeriod(body.getRotationPeriod())
                .semiMajorAxis(body.getSemiMajorAxis())
                .surfaceGravity(body.getSurfaceGravity())
                .surfacePressure(body.getSurfacePressure())
                .surfaceTemperature(body.getSurfaceTemperature())
                .system(Optional.ofNullable(body.getSystem())
                        .map(system -> systemEntityMapper.map(system))
                        .orElse(null))
                .systemAddress(body.getSystemAddress())
                .terraformState(body.getTerraformState())
                .tidalLock(body.getTidalLock())
                .volcanism(body.getVolcanism())
                .horizons(body.getHorizons())
                .odyssey(body.getOdyssey())
                .estimatedScanValue(body.getEstimatedScanValue())
                .build();
    }
}
