package io.edpn.backend.trade.application.dto.persistence.entity.mapper;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.application.dto.persistence.entity.StationDataRequestEntity;

public interface StationDataRequestEntityMapper {

    StationDataRequest map(StationDataRequestEntity stationDataRequestEntity);

}
