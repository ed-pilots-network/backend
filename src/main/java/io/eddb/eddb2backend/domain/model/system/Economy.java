package io.eddb.eddb2backend.domain.model.system;

import lombok.Builder;

@Builder
public record Economy(Long id, String name) {
}