package io.eddb.eddb2backend.domain.model.common;

import lombok.Builder;

@Builder
public record Allegiance(Long id, String name) {
}
