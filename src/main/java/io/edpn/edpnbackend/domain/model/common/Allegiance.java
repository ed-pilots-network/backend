package io.edpn.edpnbackend.domain.model.common;

import lombok.Builder;

@Builder
public record Allegiance(Long id, String name) {
}
