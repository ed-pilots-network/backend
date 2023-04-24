package io.edpn.backend.eddnrest.domain.model.system;

import lombok.Builder;

@Builder
public record ReserveType(Long id, String name) {
}
