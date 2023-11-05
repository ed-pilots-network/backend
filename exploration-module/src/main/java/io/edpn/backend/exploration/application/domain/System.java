package io.edpn.backend.exploration.application.domain;

import lombok.Builder;

import java.util.UUID;

@Builder
public record System(
        UUID id,
        Long eliteId,
        String name,
        String primaryStarClass,
        Coordinate coordinate
) {
}
