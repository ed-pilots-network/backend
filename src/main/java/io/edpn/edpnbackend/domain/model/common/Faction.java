package io.edpn.edpnbackend.domain.model.common;

import lombok.Builder;

@Builder
public record Faction(Long id, String name) {
}
