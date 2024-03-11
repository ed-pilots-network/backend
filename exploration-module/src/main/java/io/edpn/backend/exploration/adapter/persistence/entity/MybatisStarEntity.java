package io.edpn.backend.exploration.adapter.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class MybatisStarEntity {
    private UUID id;
    private Double absoluteMagnitude;
    private Long age;
    private Double arrivalDistance;
    private Double axialTilt;
    private Boolean discovered;
    private Long localId;
    private String luminosity;
    private Boolean mapped;
    private String name;
    private Double radius;
    private List<MybatisRingEntity> rings;
    private Double rotationalPeriod;
    private String starType;
    private Long stellarMass;
    private Integer subclass;
    private Double surfaceTemperature;
    private MybatisSystemEntity system;
    private Long systemAddress;
    private Boolean horizons;
    private Boolean odyssey;
    private Double estimatedScanValue;
}
