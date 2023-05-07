package io.edpn.backend.rest.domain.model.common;

import lombok.Builder;

@Builder
public record Faction(Long id, String name) {
}
