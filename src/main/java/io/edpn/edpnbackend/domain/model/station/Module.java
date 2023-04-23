package io.edpn.edpnbackend.domain.model.station;

import lombok.Builder;

@Builder
public record Module(Long id, String name) {
}
