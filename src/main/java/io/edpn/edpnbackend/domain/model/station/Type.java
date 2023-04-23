package io.edpn.edpnbackend.domain.model.station;

import lombok.Builder;

@Builder
public record Type(Long id, String name) {
}
