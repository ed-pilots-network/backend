package io.edpn.backend.rest.domain.model.body;

import io.edpn.backend.rest.domain.model.system.System;
import lombok.Builder;

@Builder
public record Body(Long id, String name, System system) {
}
