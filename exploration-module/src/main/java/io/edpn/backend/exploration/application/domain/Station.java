package io.edpn.backend.exploration.application.domain;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.With;

@With
@Builder
public record Station(
        UUID id,
        Long marketId,
        String name,
        String type,
        Double distanceFromStar,
        System system,
        Map<LandingPadSize, Integer> landingPads,
        Map<String, Double> economies,
        String economy,
        String government,
        Boolean odyssey,
        LocalDateTime updatedAt
        ) {
}
