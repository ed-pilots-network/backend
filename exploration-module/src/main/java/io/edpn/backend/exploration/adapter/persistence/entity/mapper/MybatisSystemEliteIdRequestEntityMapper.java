package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEliteIdRequestEntity;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.dto.SystemEliteIdRequestEntity;
import io.edpn.backend.exploration.application.dto.mapper.SystemEliteIdRequestEntityMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisSystemEliteIdRequestEntityMapper implements SystemEliteIdRequestEntityMapper<MybatisSystemEliteIdRequestEntity> {

    @Override
    public SystemEliteIdRequest map(SystemEliteIdRequestEntity systemEliteIdRequestEntity) {
        return new SystemEliteIdRequest(systemEliteIdRequestEntity.getSystemName(), systemEliteIdRequestEntity.getRequestingModule());
    }

    @Override
    public MybatisSystemEliteIdRequestEntity map(SystemEliteIdRequest systemEliteIdRequest) {
        return new MybatisSystemEliteIdRequestEntity(
                systemEliteIdRequest.systemName(),
                systemEliteIdRequest.requestingModule());
    }
}
