package io.edpn.backend.exploration.application.domain;

import lombok.With;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@With
public record Station(
        UUID id,
        Long marketId,
        String name,
        String type,
        Double distanceFromStar,
        System system,
        Map<LandingPadSize, Integer> landingPads,
        Map<String, Double> economies,
        List<String> services,
        String economy,
        String government,
        Boolean odyssey,
        Boolean horizons,
        LocalDateTime updatedAt
) {
    public Station {
        if (landingPads == null) {
            landingPads = Map.of();
        }

        if (economies == null) {
            economies = Map.of();
        }

        if (services == null) {
            services = List.of();
        }
    }
}
