package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationDataRequestEntity;

public class MybatisStationDataRequestEntityMapper {
    public StationDataRequest map(MybatisStationDataRequestEntity mybatisStationDataRequestEntity) {
        return new StationDataRequest(null, mybatisStationDataRequestEntity.getStationName(), mybatisStationDataRequestEntity.getSystemName());
    }
}
