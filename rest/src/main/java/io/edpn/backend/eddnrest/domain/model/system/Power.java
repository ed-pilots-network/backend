package io.edpn.backend.eddnrest.domain.model.system;

import lombok.Builder;

@Builder
public record Power(Long id, String name) {
}
