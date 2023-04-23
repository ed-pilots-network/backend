package io.edpn.edpnbackend.domain.model.system;

import lombok.Builder;

@Builder
public record PowerState(Long id, String name) {
}
