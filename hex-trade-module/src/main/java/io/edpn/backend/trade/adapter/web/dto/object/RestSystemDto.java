package io.edpn.backend.trade.adapter.web.dto.object;

import io.edpn.backend.trade.application.dto.web.object.SystemDto;

public record RestSystemDto(
        Long eliteId,
        String name,
        Double xCoordinate,
        Double yCoordinate,
        Double zCoordinate) implements SystemDto {
}