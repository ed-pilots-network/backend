package io.edpn.edpnbackend.domain.model.body;

import io.edpn.edpnbackend.domain.model.system.System;
import lombok.Builder;

@Builder
public record Body(Long id, String name, System system) {
}
