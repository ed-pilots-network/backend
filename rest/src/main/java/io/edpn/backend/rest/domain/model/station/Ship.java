package io.edpn.backend.rest.domain.model.station;

import lombok.Builder;

@Builder
public record Ship(Long id, String name) {
}
