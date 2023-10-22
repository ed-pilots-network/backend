package io.edpn.backend.exploration.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Star{
    private UUID id;
    private Double absoluteMagnitude;
    private Long age; // millions of years
    private Double arrivalDistance;// LS
    private Double axialTilt;
    private Boolean discovered;
    private Long localId;
    private String luminosity;
    private Boolean mapped;
    private String name;
    private Long radius;
    private List<Ring> rings;
    private Double rotationalPeriod;
    private String starType;
    private Long stellarMass; // in multiples of Sol
    private Integer subclass;
    private Long surfaceTemperature;
    private System system;
    private Long systemAddress;
    private Boolean horizons;
    private Boolean odyssey;
    private Double estimatedScanValue;
}