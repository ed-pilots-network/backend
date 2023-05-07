package io.edpn.backend.rest.domain.model.station;

import lombok.Builder;

@Builder
public record Module(Long id, String name) {
}
