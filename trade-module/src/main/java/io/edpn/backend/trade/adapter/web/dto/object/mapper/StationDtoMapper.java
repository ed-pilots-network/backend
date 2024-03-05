package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.StationDto;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.Station;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class StationDtoMapper {

    private final SystemDtoMapper systemDtoMapper;

    public StationDto map(Station station) {
        return new StationDto(
                station.marketId(),
                station.name(),
                station.arrivalDistance(),
                systemDtoMapper.map(station.system()),
                station.planetary(),
                station.requireOdyssey(),
                station.fleetCarrier(),
                String.valueOf(Optional.ofNullable(station.maxLandingPadSize()).orElse(LandingPadSize.UNKNOWN)),
                station.marketUpdatedAt()

        );
    }
}
