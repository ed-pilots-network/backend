package io.edpn.edpnbackend.domain.model.station;

import lombok.Builder;

@Builder
public record Ship(Long id, String name) {
}
