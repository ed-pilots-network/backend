package io.edpn.backend.trade.application.domain;

import lombok.With;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@With
public record Station(
        UUID id,
        Long marketId,
        String name,
        Double arrivalDistance,
        System system,
        Boolean planetary,
        Boolean requireOdyssey,
        Boolean fleetCarrier,
        LandingPadSize maxLandingPadSize,
        LocalDateTime marketUpdatedAt,
        List<MarketDatum> marketData
) {
}