package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestStationDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestSystemDto;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.dto.web.object.StationDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.StationDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.SystemDtoMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RestStationDtoMapper implements StationDtoMapper {
    
    private final SystemDtoMapper systemDtoMapper;
    
    @Override
    public StationDto map(Station station) {
        return new RestStationDto(
                station.getMarketId(),
                station.getName(),
                station.getArrivalDistance(),
                (RestSystemDto)systemDtoMapper.map(station.getSystem()),
                station.getPlanetary(),
                station.getRequireOdyssey(),
                station.getFleetCarrier(),
                String.valueOf(Optional.ofNullable(station.getMaxLandingPadSize()).orElse(LandingPadSize.UNKNOWN)),
                station.getMarketUpdatedAt()
                
        );
    }
}
