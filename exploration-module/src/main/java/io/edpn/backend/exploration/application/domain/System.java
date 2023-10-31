package io.edpn.backend.exploration.application.domain;

import java.util.UUID;
import lombok.Builder;

@Builder
public record System(
        UUID id,
        Long eliteId,
        String name,
        String starClass,
        Coordinate coordinate
) {
}
