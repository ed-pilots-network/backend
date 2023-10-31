package io.edpn.backend.exploration.application.domain;

import java.util.UUID;
import lombok.Builder;

@Builder
public record Ring(
        UUID id,
        Long innerRadius,
        Long mass, // MT MegaTonnes
        String name,
        Long outerRadius,
        String ringClass,
        UUID bodyId,
        UUID starId
) {
}