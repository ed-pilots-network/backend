package io.eddb.eddb2backend.domain.model.system;

import lombok.Builder;

@Builder
public record Power(Long id, String name) {
}