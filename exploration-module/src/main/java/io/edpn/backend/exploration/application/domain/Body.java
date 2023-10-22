package io.edpn.backend.exploration.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
//Planet or Moon
public class Body {
    private UUID id;
    private Double arrivalDistance; // LS
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
    private Map<String, Integer> parents; // ID's are system local
    private Double periapsis;
    private String planetClass;
    private Double radius;
    private List<Ring> rings;
    private Double rotationPeriod; // seconds
    private Double semiMajorAxis;
    private Double surfaceGravity;
    private Double surfacePressure;
    private Double surfaceTemperature;
    private System system;
    private Long systemAddress;
    private String terraformState;
    private Boolean tidalLock;
    private String volcanism;
    private Boolean horizons;
    private Boolean odyssey;
    private Double estimatedScanValue;
}