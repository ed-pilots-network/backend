package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestStationDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestSystemDto;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.dto.web.object.StationDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.StationDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.SystemDtoMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestStationDtoMapper implements StationDtoMapper {
    
    private final SystemDtoMapper systemDtoMapper;
    
    @Override
    public StationDto map(Station station) {
        return new RestStationDto(
                station.marketId(),
                station.name(),
                station.arrivalDistance(),
                (RestSystemDto)systemDtoMapper.map(station.system()),
                station.planetary(),
                station.requireOdyssey(),
                station.fleetCarrier(),
                String.valueOf(station.maxLandingPadSize()),
                station.marketUpdatedAt()
                
        );
    }
}
