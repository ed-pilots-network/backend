package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemCoordinateRequestEntity;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisSystemCoordinateRequestEntityMapper {

    public SystemCoordinateRequest map(MybatisSystemCoordinateRequestEntity systemCoordinateDataRequestEntity) {
        return new SystemCoordinateRequest(systemCoordinateDataRequestEntity.getSystemName(), systemCoordinateDataRequestEntity.getRequestingModule());
    }

    public MybatisSystemCoordinateRequestEntity map(SystemCoordinateRequest systemCoordinateDataRequest) {
        return new MybatisSystemCoordinateRequestEntity(
                systemCoordinateDataRequest.systemName(),
                systemCoordinateDataRequest.requestingModule());
    }
}
