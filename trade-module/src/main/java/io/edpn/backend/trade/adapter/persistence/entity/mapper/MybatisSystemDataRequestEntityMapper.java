package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.application.dto.persistence.entity.SystemDataRequestEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.SystemDataRequestEntityMapper;

public class MybatisSystemDataRequestEntityMapper implements SystemDataRequestEntityMapper {
    @Override
    public SystemDataRequest map(SystemDataRequestEntity systemDataRequestEntity) {
        return new SystemDataRequest(null, systemDataRequestEntity.getSystemName());
    }
}
