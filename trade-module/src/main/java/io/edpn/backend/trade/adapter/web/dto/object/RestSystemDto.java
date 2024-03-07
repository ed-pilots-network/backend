package io.edpn.backend.trade.adapter.web.dto.object;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Schema(name = "SystemDto")
@Builder
@Jacksonized
public record RestSystemDto(
        Long eliteId,
        String name,
        RestCoordinateDto coordinates) {
}