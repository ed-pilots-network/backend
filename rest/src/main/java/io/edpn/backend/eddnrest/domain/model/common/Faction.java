package io.edpn.backend.eddnrest.domain.model.common;

import lombok.Builder;

@Builder
public record Faction(Long id, String name) {
}
