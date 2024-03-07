package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemDataRequestEntity;

public class MybatisSystemDataRequestEntityMapper {
    public SystemDataRequest map(MybatisSystemDataRequestEntity mybatisSystemDataRequestEntity) {
        return new SystemDataRequest(null, mybatisSystemDataRequestEntity.getSystemName());
    }
}
