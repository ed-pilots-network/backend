package io.eddb.eddb2backend.domain.model.system;

import lombok.Builder;

@Builder
public record Allegiance(Long id, String name) {
}
