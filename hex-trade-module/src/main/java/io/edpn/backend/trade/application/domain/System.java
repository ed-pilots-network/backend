package io.edpn.backend.trade.application.domain;

import java.util.UUID;

public record System(
        UUID id,
        Long eliteId,
        String name,
        Double xCoordinate,
        Double yCoordinate,
        Double zCoordinate
) {
}
