package io.edpn.backend.exploration.application.domain;

import java.util.List;
import java.util.Map;

//Planet or Moon
public record Body(
        Double arrivalDistance, // LS
        Double ascendingNode,
        String atmosphere,
        Map<String, Double> atmosphericComposition,
        Double axialTilt,
        Map<String, Double> bodyComposition,
        Boolean discovered,
        Boolean mapped,
        String name,
        Long eliteID,
        Double eccentricity,
        Boolean landable,
        Double mass,
        Double meanAnomaly,
        Double orbitalInclination,
        Double orbitalPeriod,
        Map<String, Integer> parents, // ID's are system local
        Double periapsis,
        String planetClass,
        Double radius,
        List<Ring> rings,
        Double rotationPeriod, // seconds
        Double semiMajorAxis,
        Double surfaceGravity,
        Double surfacePressure,
        Double surfaceTemperature,
        System system,
        String terraformState,
        Boolean tidalLock,
        String volcanism,
        Boolean horizons,
        Boolean odyssey,
        Double estimatedScanValue) {
}