package io.edpn.edpnbackend.domain.model.common;

import lombok.Builder;

@Builder
public record Economy(Long id, String name) {
}