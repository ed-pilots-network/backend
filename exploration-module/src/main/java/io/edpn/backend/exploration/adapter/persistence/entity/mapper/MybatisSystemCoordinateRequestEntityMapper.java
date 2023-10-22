package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemCoordinateRequestEntity;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.persistence.entity.SystemCoordinateRequestEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemCoordinateRequestEntityMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisSystemCoordinateRequestEntityMapper implements SystemCoordinateRequestEntityMapper<MybatisSystemCoordinateRequestEntity> {

    @Override
    public SystemCoordinateRequest map(SystemCoordinateRequestEntity systemCoordinateDataRequestEntity) {
        return new SystemCoordinateRequest(systemCoordinateDataRequestEntity.getSystemName(), systemCoordinateDataRequestEntity.getRequestingModule());
    }

    @Override
    public MybatisSystemCoordinateRequestEntity map(SystemCoordinateRequest systemCoordinateDataRequest) {
        return new MybatisSystemCoordinateRequestEntity(
                systemCoordinateDataRequest.getSystemName(),
                systemCoordinateDataRequest.getRequestingModule());
    }
}
