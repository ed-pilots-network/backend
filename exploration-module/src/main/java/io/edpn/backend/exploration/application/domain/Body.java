package io.edpn.backend.exploration.application.domain;

//TODO: Estimated value + other scan fields
public record Body(
        Long eliteID,
        Double eccentricity,
        Double meanAnomaly,
        Double orbitalInclination,
        Double orbitalPeriod,
        Double periapsis,
        Double semiMajorAxis,
        System system,
        Boolean horizons,
        Boolean odyssey) {
}
