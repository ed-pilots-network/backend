package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEliteIdRequestEntity;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisSystemEliteIdRequestEntityMapper {

    public SystemEliteIdRequest map(MybatisSystemEliteIdRequestEntity systemEliteIdRequestEntity) {
        return new SystemEliteIdRequest(systemEliteIdRequestEntity.getSystemName(), systemEliteIdRequestEntity.getRequestingModule());
    }

    public MybatisSystemEliteIdRequestEntity map(SystemEliteIdRequest systemEliteIdRequest) {
        return new MybatisSystemEliteIdRequestEntity(
                systemEliteIdRequest.systemName(),
                systemEliteIdRequest.requestingModule());
    }
}
