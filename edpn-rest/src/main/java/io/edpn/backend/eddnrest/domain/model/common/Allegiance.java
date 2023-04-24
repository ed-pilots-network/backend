package io.edpn.backend.eddnrest.domain.model.common;

import lombok.Builder;

@Builder
public record Allegiance(Long id, String name) {
}
