package io.eddb.eddb2backend.domain.model.body;

import lombok.Builder;

@Builder
public record Body(Long id, String name) {
}
