package io.edpn.backend.trade.application.dto.web.object.mapper;

import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.dto.web.object.StationDto;

public interface StationDtoMapper {
    
    StationDto map(Station station);
}
