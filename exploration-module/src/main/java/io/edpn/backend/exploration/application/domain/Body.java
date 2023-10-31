package io.edpn.backend.exploration.application.domain;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;


@Builder
public record Body(
        UUID id,
        Double arrivalDistance, // Ls
        Double ascendingNode,
        String atmosphere,
        Map<String, Double> atmosphericComposition,
        Double axialTilt,
        Map<String, Double> bodyComposition,
        Boolean discovered,
        Boolean mapped,
        String name,
        Long localId,
        Double eccentricity,
        Boolean landable,
        Double mass,
        Map<String, Double> materials,
        Double meanAnomaly,
        Double orbitalInclination,
        Double orbitalPeriod,
        Map<Integer, String> parents, // ID's are system local
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
        Long systemAddress,
        String terraformState,
        Boolean tidalLock,
        String volcanism,
        Boolean horizons,
        Boolean odyssey,
        Double estimatedScanValue) {
}