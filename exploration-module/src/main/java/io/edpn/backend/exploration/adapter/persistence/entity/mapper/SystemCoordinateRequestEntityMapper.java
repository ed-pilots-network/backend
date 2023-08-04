package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.SystemCoordinateRequestEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SystemCoordinateRequestEntityMapper implements io.edpn.backend.exploration.application.dto.mapper.SystemCoordinateRequestEntityMapper {

    @Override
    public SystemCoordinateRequest map(SystemCoordinateRequestEntity systemCoordinateDataRequestEntity) {
        return new SystemCoordinateRequest(systemCoordinateDataRequestEntity.getSystemName(), systemCoordinateDataRequestEntity.getRequestingModule());
    }

    @Override
    public io.edpn.backend.exploration.adapter.persistence.entity.SystemCoordinateRequestEntity map(SystemCoordinateRequest systemCoordinateDataRequest) {
        return new io.edpn.backend.exploration.adapter.persistence.entity.SystemCoordinateRequestEntity(
                systemCoordinateDataRequest.systemName(),
                systemCoordinateDataRequest.requestingModule());
    }
}
