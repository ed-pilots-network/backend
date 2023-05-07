package io.edpn.backend.eddnrest.domain.model.system;

import lombok.Builder;

@Builder
public record Security(Long id, String name) {
}
