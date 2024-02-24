package io.edpn.backend.exploration.application.domain;

import io.edpn.backend.util.Module;

public record StationMaxLandingPadSizeRequest(
        String systemName,
        String stationName,
        Module requestingModule) {
}
