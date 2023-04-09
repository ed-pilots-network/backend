package io.eddb.eddb2backend.domain.model.station;

import lombok.Builder;

@Builder
public record Type(Long id, String name) {
}
