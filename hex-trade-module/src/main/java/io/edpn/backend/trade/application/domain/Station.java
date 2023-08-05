package io.edpn.backend.trade.application.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record Station(
        UUID id,
        Long marketId,
        String name,
        Double arrivalDistance,
        System system,
        Boolean planetary,
        Boolean requireOdyssey,
        Boolean fleetCarrier,
        LandingPadSize maxLandingPadSiz,
        LocalDateTime marketUpdatedAt,
        List<MarketDatum> marketData
) {
}
