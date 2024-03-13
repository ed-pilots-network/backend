package io.edpn.backend.exploration.application.domain.intermodulecommunication;

import io.edpn.backend.exploration.application.domain.Station;

public record StationArrivalDistanceResponse(
        String stationName,

        String systemName,

        double arrivalDistance
) implements io.edpn.backend.messageprocessorlib.application.dto.edpn.intermodulecommunication.StationArrivalDistanceResponse {
    public static StationArrivalDistanceResponse from(Station station) {
        return new StationArrivalDistanceResponse(
                station.name(),
                station.system().name(),
                station.distanceFromStar());
    }
}
