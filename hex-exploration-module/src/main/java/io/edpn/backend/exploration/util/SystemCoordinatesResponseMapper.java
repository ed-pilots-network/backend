package io.edpn.backend.exploration.util;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;

public class SystemCoordinatesResponseMapper {

    public static SystemCoordinatesResponse map(System system) {
        SystemCoordinatesResponse systemCoordinatesResponse = new SystemCoordinatesResponse();
        systemCoordinatesResponse.setSystemName(system.getName());
        systemCoordinatesResponse.setXCoordinate(system.getCoordinate().x());
        systemCoordinatesResponse.setYCoordinate(system.getCoordinate().y());
        systemCoordinatesResponse.setZCoordinate(system.getCoordinate().z());

        return systemCoordinatesResponse;
    }
}
