package io.edpn.backend.trade.application.dto.persistence.entity.mapper;

import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.dto.persistence.entity.StationEntity;

public interface StationEntityMapper<T extends StationEntity> {
    
    Station map(StationEntity stationEntity);
    
    T map(Station station);
}
