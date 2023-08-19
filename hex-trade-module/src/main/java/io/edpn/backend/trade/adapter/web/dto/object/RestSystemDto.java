package io.edpn.backend.trade.adapter.web.dto.object;

import io.edpn.backend.trade.application.dto.web.object.SystemDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SystemDto")
public record RestSystemDto(
        Long eliteId,
        String name,
        Double xCoordinate,
        Double yCoordinate,
        Double zCoordinate) implements SystemDto {
}