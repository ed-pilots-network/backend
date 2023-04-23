package io.edpn.edpnbackend.domain.model.system;

import lombok.Builder;

@Builder
public record ReserveType(Long id, String name) {
}
