package io.edpn.backend.exploration.application.domain;

import java.util.List;

public record Star(
        Double absoluteMagnitude,
        Long age, // millions of years
        Double arrivalDistance,// LS
        Boolean discovered,
        Long eliteId,
        String luminosity,
        Boolean mapped,
        String name,
        Long radius,
        List<Ring> rings,
        Double rotationalPeriod,
        String starType,
        Long stellarMass, // in multiples of Sol
        Long surfaceTemperature,
        Boolean horizons,
        Boolean odyssey,
        Double estimatedScanValue) {
}