package io.edpn.backend.eddnrest.domain.model.station;

import lombok.Builder;

@Builder
public record Module(Long id, String name) {
}
