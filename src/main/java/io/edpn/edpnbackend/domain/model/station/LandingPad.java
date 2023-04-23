package io.edpn.edpnbackend.domain.model.station;

import lombok.Builder;

@Builder
public record LandingPad(Long id, char size) {
}
