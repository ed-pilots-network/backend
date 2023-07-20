package io.edpn.backend.exploration.infrastructure.persistence.mappers.entity;

import io.edpn.backend.exploration.domain.model.SystemCoordinateDataRequest;
import io.edpn.backend.exploration.infrastructure.persistence.entity.SystemCoordinateDataRequestEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SystemCoordinateDataRequestMapper {

    public SystemCoordinateDataRequest map(SystemCoordinateDataRequestEntity systemCoordinateDataRequestEntity) {
        return SystemCoordinateDataRequest.builder()
                .systemName(systemCoordinateDataRequestEntity.getSystemName())
                .requestingModule(systemCoordinateDataRequestEntity.getRequestingModule())
                .build();
    }

    public SystemCoordinateDataRequestEntity map(SystemCoordinateDataRequest systemCoordinateDataRequest) {
        return SystemCoordinateDataRequestEntity.builder()
                .systemName(systemCoordinateDataRequest.getSystemName())
                .requestingModule(systemCoordinateDataRequest.getRequestingModule())
                .build();
    }
}
