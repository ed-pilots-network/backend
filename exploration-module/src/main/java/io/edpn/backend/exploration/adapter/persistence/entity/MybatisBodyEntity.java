package io.edpn.backend.exploration.adapter.persistence.entity;

import io.edpn.backend.exploration.application.dto.persistence.entity.BodyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class MybatisBodyEntity implements BodyEntity {
    private UUID id;
    private Double arrivalDistance;
    private Double ascendingNode;
    private String atmosphere;
    private Map<String, Double> atmosphericComposition;
    private Double axialTilt;
    private Map<String, Double> bodyComposition;
    private Boolean discovered;
    private Boolean mapped;
    private String name;
    private Long localId;
    private Double eccentricity;
    private Boolean landable;
    private Double mass;
    private Map<String, Double> materials;
    private Double meanAnomaly;
    private Double orbitalInclination;
    private Double orbitalPeriod;
    private Map<Integer, String> parents;
    private Double periapsis;
    private String planetClass;
    private Double radius;
    private List<MybatisRingEntity> rings;
    private Double rotationPeriod;
    private Double semiMajorAxis;
    private Double surfaceGravity;
    private Double surfacePressure;
    private Double surfaceTemperature;
    private MybatisSystemEntity system;
    private Long systemAddress;
    private String terraformState;
    private Boolean tidalLock;
    private String volcanism;
    private Boolean horizons;
    private Boolean odyssey;
    private Double estimatedScanValue;
}
