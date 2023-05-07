package io.edpn.backend.eddnrest.domain.model.system;

import lombok.Builder;

@Builder
public record PowerState(Long id, String name) {
}
