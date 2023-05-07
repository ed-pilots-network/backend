package io.edpn.backend.eddnrest.domain.model.station;

import lombok.Builder;

@Builder
public record Type(Long id, String name) {
}
