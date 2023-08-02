package io.edpn.backend.exploration.adapter.kafka.dto;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SystemCoordinatesResponseMapper {

    public SystemCoordinatesResponse map(System system) {
        SystemCoordinatesResponse systemCoordinatesResponse = new SystemCoordinatesResponse();
        systemCoordinatesResponse.setSystemName(system.getName());
        systemCoordinatesResponse.setXCoordinate(system.getCoordinate().x());
        systemCoordinatesResponse.setYCoordinate(system.getCoordinate().y());
        systemCoordinatesResponse.setZCoordinate(system.getCoordinate().z());

        return systemCoordinatesResponse;
    }
}
