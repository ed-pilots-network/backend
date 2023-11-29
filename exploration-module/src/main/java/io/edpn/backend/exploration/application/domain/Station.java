package io.edpn.backend.exploration.application.domain;

import lombok.With;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@With
public record Station(
        UUID id,
        Long marketId,
        String name,
        Double distanceFromStar,
        System system,
        Map<LandingPadSize, Integer> landingPads,
        Map<String, Double> economies,
        String stationEconomy,
        Faction stationFaction,
        String stationGovernment,
        Boolean requireOdyssey,
        LocalDateTime updatedAt
        ) {
}