package io.edpn.backend.exploration.application.domain.intermodulecommunication;

import io.edpn.backend.exploration.application.domain.System;

public record SystemCoordinatesResponse(
        String systemName,
        double xCoordinate,
        double yCoordinate,
        double zCoordinate
) implements io.edpn.backend.messageprocessorlib.application.dto.edpn.intermodulecommunication.SystemCoordinatesResponse {
    public static SystemCoordinatesResponse from(System system) {
        return new SystemCoordinatesResponse(
                system.name(),
                system.coordinate().x(),
                system.coordinate().y(),
                system.coordinate().z());
    }
}
