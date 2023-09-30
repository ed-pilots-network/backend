package io.edpn.backend.trade.application.dto.persistence.entity.mapper;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.application.dto.persistence.entity.SystemDataRequestEntity;

public interface SystemDataRequestEntityMapper {

    SystemDataRequest map(SystemDataRequestEntity systemDataRequestEntity);
}
