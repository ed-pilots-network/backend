package io.edpn.backend.exploration.application.domain.intermodulecommunication;

import io.edpn.backend.exploration.application.domain.LandingPadSize;
import io.edpn.backend.exploration.application.domain.Station;

import java.util.Map;
import java.util.Objects;

public record StationMaxLandingPadSizeResponse(
        String stationName,

        String systemName,

        String maxLandingPadSize
) implements io.edpn.backend.messageprocessorlib.application.dto.edpn.intermodulecommunication.StationMaxLandingPadSizeResponse {

    public static StationMaxLandingPadSizeResponse from(Station station) {

        return new StationMaxLandingPadSizeResponse(
                station.name(),
                station.system().name(),
                getMaxLandingPadSize(station.landingPads()).name());
    }

    private static LandingPadSize getMaxLandingPadSize(Map<LandingPadSize, Integer> landingPads) {
        if (Objects.isNull(landingPads) || landingPads.isEmpty()) {
            return LandingPadSize.UNKNOWN;
        }

        if (landingPads.getOrDefault(LandingPadSize.LARGE, 0) > 0) {
            return LandingPadSize.LARGE;
        }
        if (landingPads.getOrDefault(LandingPadSize.MEDIUM, 0) > 0) {
            return LandingPadSize.MEDIUM;
        }
        if (landingPads.getOrDefault(LandingPadSize.SMALL, 0) > 0) {
            return LandingPadSize.SMALL;
        }

        return LandingPadSize.UNKNOWN;
    }
}
