package io.edpn.backend.exploration.adapter.persistence.entity;

import java.util.UUID;

public record SystemEntity(UUID id, String name,
                           CoordinateEntity coordinates,
                           Long eliteId,
                           String starClass) implements io.edpn.backend.exploration.application.dto.SystemDto {
}

