package io.edpn.backend.exploration.adapter.persistence.entity;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.persistence.entity.BodyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class MybatisBodyEntity implements BodyEntity {
    private UUID id;
    private Long eliteID;
    private Double eccentricity;
    private Double meanAnomaly;
    private Double orbitalInclination;
    private Double orbitalPeriod;
    private Double periapsis;
    private Double semiMajorAxis;
    private System system;
    private Boolean horizons;
    private Boolean odyssey;
    private Double estimatedScanValue;
}
