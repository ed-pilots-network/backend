package io.edpn.backend.trade.application.domain;

import lombok.With;

import java.util.UUID;

@With
public record System(
        UUID id,
        Long eliteId,
        String name,
        Coordinate coordinate
) {
}
