package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.SystemCoordinateRequestEntity;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.SystemCoordinateRequestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SystemCoordinateRequestEntityMapper implements io.edpn.backend.exploration.application.dto.mapper.SystemCoordinateRequestEntityMapper {

    @Override
    public SystemCoordinateRequest map(SystemCoordinateRequestDto systemCoordinateDataRequestEntity) {
        return new SystemCoordinateRequest(systemCoordinateDataRequestEntity.systemName(), systemCoordinateDataRequestEntity.requestingModule());
    }

    @Override
    public SystemCoordinateRequestEntity map(SystemCoordinateRequest systemCoordinateDataRequest) {
        return new SystemCoordinateRequestEntity(
                systemCoordinateDataRequest.systemName(),
                systemCoordinateDataRequest.requestingModule());
    }
}
