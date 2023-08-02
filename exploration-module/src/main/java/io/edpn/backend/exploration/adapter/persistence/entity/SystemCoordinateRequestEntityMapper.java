package io.edpn.backend.exploration.adapter.persistence.entity;

import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SystemCoordinateRequestEntityMapper {

    public SystemCoordinateRequest map(SystemCoordinateRequestEntity systemCoordinateDataRequestEntity) {
        return new SystemCoordinateRequest(systemCoordinateDataRequestEntity.getSystemName(), systemCoordinateDataRequestEntity.getRequestingModule());
    }

    public SystemCoordinateRequestEntity map(SystemCoordinateRequest systemCoordinateDataRequest) {
        return SystemCoordinateRequestEntity.builder()
                .systemName(systemCoordinateDataRequest.systemName())
                .requestingModule(systemCoordinateDataRequest.requestingModule())
                .build();
    }
}
