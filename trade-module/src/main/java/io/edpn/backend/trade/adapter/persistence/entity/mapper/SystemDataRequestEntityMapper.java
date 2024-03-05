package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.adapter.persistence.entity.SystemDataRequestEntity;

public class SystemDataRequestEntityMapper {
    public SystemDataRequest map(SystemDataRequestEntity systemDataRequestEntity) {
        return new SystemDataRequest(null, systemDataRequestEntity.getSystemName());
    }
}
