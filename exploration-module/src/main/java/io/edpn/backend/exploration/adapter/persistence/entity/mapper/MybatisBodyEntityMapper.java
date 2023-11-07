package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisBodyEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.exploration.application.domain.Body;
import io.edpn.backend.exploration.application.dto.persistence.entity.BodyEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.BodyEntityMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.RingEntityMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEntityMapper;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisBodyEntityMapper implements BodyEntityMapper<MybatisBodyEntity> {
    
    private final RingEntityMapper<MybatisRingEntity> ringEntityMapper;
    private final SystemEntityMapper<MybatisSystemEntity> systemEntityMapper;
    
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
                        .stream()
                        .map(ringEntityMapper::map)
                        .toList(),
                bodyEntity.getRotationPeriod(),
                bodyEntity.getSemiMajorAxis(),
                bodyEntity.getSurfaceGravity(),
                bodyEntity.getSurfacePressure(),
                bodyEntity.getSurfaceTemperature(),
                Optional.ofNullable(bodyEntity.getSystem())
                        .map(systemEntityMapper::map)
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
                .id(body.id())
                .arrivalDistance(body.arrivalDistance())
                .ascendingNode(body.ascendingNode())
                .atmosphere(body.atmosphere())
                .atmosphericComposition(body.atmosphericComposition())
                .axialTilt(body.axialTilt())
                .bodyComposition(body.bodyComposition())
                .discovered(body.discovered())
                .mapped(body.mapped())
                .name(body.name())
                .localId(body.localId())
                .eccentricity(body.eccentricity())
                .landable(body.landable())
                .mass(body.mass())
                .materials(body.materials())
                .meanAnomaly(body.meanAnomaly())
                .orbitalInclination(body.orbitalInclination())
                .orbitalPeriod(body.orbitalPeriod())
                .parents(body.parents())
                .periapsis(body.periapsis())
                .planetClass(body.planetClass())
                .radius(body.radius())
                .rings(Optional.ofNullable(body.rings())
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(ringEntityMapper::map)
                        .toList())
                .rotationPeriod(body.rotationPeriod())
                .semiMajorAxis(body.semiMajorAxis())
                .surfaceGravity(body.surfaceGravity())
                .surfacePressure(body.surfacePressure())
                .surfaceTemperature(body.surfaceTemperature())
                .system(Optional.ofNullable(body.system())
                        .map(systemEntityMapper::map)
                        .orElse(null))
                .systemAddress(body.systemAddress())
                .terraformState(body.terraformState())
                .tidalLock(body.tidalLock())
                .volcanism(body.volcanism())
                .horizons(body.horizons())
                .odyssey(body.odyssey())
                .estimatedScanValue(body.estimatedScanValue())
                .build();
    }
}
