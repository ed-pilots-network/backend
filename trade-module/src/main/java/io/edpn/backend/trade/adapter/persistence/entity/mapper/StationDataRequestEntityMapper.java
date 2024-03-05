package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.adapter.persistence.entity.StationDataRequestEntity;

public class StationDataRequestEntityMapper {
    public StationDataRequest map(StationDataRequestEntity stationDataRequestEntity) {
        return new StationDataRequest(null, stationDataRequestEntity.getStationName(), stationDataRequestEntity.getSystemName());
    }
}
