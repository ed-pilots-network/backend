package io.edpn.backend.rest.domain.model.common;

import lombok.Builder;

@Builder
public record Allegiance(Long id, String name) {
}
