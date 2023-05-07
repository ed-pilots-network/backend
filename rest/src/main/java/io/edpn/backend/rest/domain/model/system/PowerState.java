package io.edpn.backend.rest.domain.model.system;

import lombok.Builder;

@Builder
public record PowerState(Long id, String name) {
}
