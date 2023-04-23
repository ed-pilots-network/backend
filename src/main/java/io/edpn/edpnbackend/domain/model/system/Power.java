package io.edpn.edpnbackend.domain.model.system;

import lombok.Builder;

@Builder
public record Power(Long id, String name) {
}