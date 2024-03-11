package io.edpn.backend.trade.application.domain.intermodulecommunication;

public record SystemCoordinatesResponse(
        String systemName,
        double xCoordinate,
        double yCoordinate,
        double zCoordinate
) implements io.edpn.backend.messageprocessorlib.application.dto.edpn.intermodulecommunication.SystemCoordinatesResponse {
}
