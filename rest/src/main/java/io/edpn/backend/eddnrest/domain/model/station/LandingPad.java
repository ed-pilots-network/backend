package io.edpn.backend.eddnrest.domain.model.station;

import lombok.Builder;

@Builder
public record LandingPad(Long id, char size) {
}
