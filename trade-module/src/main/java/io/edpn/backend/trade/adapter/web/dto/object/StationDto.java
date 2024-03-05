package io.edpn.backend.trade.adapter.web.dto.object;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Schema(name = "StationDto")
@Builder
@Jacksonized
public record StationDto(
        Long marketId,
        String name,
        Double arrivalDistance,
        SystemDto system,
        Boolean planetary,
        Boolean requireOdyssey,
        Boolean fleetCarrier,
        String maxLandingPadSize,
        LocalDateTime marketUpdatedAt) {
}
