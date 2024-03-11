package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationDataRequestEntity;
import io.edpn.backend.trade.application.domain.intermodulecommunication.StationDataRequest;

public class MybatisStationDataRequestEntityMapper {
    public StationDataRequest map(MybatisStationDataRequestEntity mybatisStationDataRequestEntity) {
        return new StationDataRequest(null, mybatisStationDataRequestEntity.getStationName(), mybatisStationDataRequestEntity.getSystemName());
    }
}
