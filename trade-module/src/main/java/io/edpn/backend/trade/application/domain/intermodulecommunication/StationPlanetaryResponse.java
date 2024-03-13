package io.edpn.backend.trade.application.domain.intermodulecommunication;

public record StationPlanetaryResponse(
        String stationName,
        String systemName,
        boolean planetary
) implements io.edpn.backend.messageprocessorlib.application.dto.edpn.intermodulecommunication.StationPlanetaryResponse {
}
