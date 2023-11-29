package io.edpn.backend.exploration.application.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record Faction(
        UUID id,
        String name,
        String government,
        String allegiance,
        String homeSystem,
        String isPlayerFaction,
        String Squadron,
        LocalDateTime updatedAt
) {
}
