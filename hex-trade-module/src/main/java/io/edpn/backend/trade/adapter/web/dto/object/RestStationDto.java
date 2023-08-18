package io.edpn.backend.trade.adapter.web.dto.object;

import io.edpn.backend.trade.application.dto.web.object.StationDto;
import io.edpn.backend.trade.application.dto.web.object.SystemDto;

import java.time.LocalDateTime;

public record RestStationDto(
        Long marketId,
        String name,
        Double arrivalDistance,
        SystemDto system,
        Boolean planetary,
        Boolean requireOdyssey,
        Boolean fleetCarrier,
        String maxLandingPadSize,
        LocalDateTime marketUpdatedAt) implements StationDto {
}
