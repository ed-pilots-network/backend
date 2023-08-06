package io.edpn.backend.exploration.adapter.kafka.dto.mapper;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KafkaSystemCoordinatesResponseMapper implements SystemCoordinatesResponseMapper {

    @Override
    public SystemCoordinatesResponse map(System system) {
        SystemCoordinatesResponse systemCoordinatesResponse = new SystemCoordinatesResponse();
        systemCoordinatesResponse.setSystemName(system.name());
        systemCoordinatesResponse.setXCoordinate(system.coordinate().x());
        systemCoordinatesResponse.setYCoordinate(system.coordinate().y());
        systemCoordinatesResponse.setZCoordinate(system.coordinate().z());

        return systemCoordinatesResponse;
    }
}
