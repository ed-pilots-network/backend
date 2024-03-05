package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestStationDto;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.Station;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RestStationDtoMapper {

    private final RestSystemDtoMapper restSystemDtoMapper;

    public RestStationDto map(Station station) {
        return new RestStationDto(
                station.marketId(),
                station.name(),
                station.arrivalDistance(),
                restSystemDtoMapper.map(station.system()),
                station.planetary(),
                station.requireOdyssey(),
                station.fleetCarrier(),
                String.valueOf(Optional.ofNullable(station.maxLandingPadSize()).orElse(LandingPadSize.UNKNOWN)),
                station.marketUpdatedAt()

        );
    }
}
