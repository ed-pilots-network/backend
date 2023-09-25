package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.application.dto.persistence.entity.StationDataRequestEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationDataRequestEntityMapper;

public class MybatisStationDataRequestEntityMapper implements StationDataRequestEntityMapper {
    @Override
    public StationDataRequest map(StationDataRequestEntity stationDataRequestEntity) {
        return new StationDataRequest(null, stationDataRequestEntity.getStationName(), stationDataRequestEntity.getSystemName());
    }
}
