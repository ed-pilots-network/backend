package io.edpn.backend.rest.domain.model.system;

import lombok.Builder;

@Builder
public record Security(Long id, String name) {
}