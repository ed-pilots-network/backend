package io.edpn.backend.eddnrest.domain.model.body;

import io.edpn.backend.eddnrest.domain.model.system.System;
import lombok.Builder;

@Builder
public record Body(Long id, String name, System system) {
}
