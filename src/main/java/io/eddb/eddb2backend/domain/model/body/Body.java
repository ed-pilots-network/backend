package io.eddb.eddb2backend.domain.model.body;

import io.eddb.eddb2backend.domain.model.system.System;
import lombok.Builder;

@Builder
public record Body(Long id, String name, System system) {
}
