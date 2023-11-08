package io.edpn.backend.trade.adapter.web.dto.object;

import io.edpn.backend.trade.application.dto.web.object.CoordinateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Schema(name = "SystemDto")
@Builder
@Jacksonized
public record RestCoordinateDto(
        Double x,
        Double y,
        Double z
) implements CoordinateDto {
}
