package io.edpn.backend.trade.application.domain.intermodulecommunication;

public record StationArrivalDistanceResponse(
        String stationName,

        String systemName,

        double arrivalDistance
) implements io.edpn.backend.messageprocessorlib.application.dto.edpn.intermodulecommunication.StationArrivalDistanceResponse {
}
