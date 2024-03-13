package io.edpn.backend.trade.application.domain.intermodulecommunication;

public record StationMaxLandingPadSizeResponse(
        String stationName,

        String systemName,

        String maxLandingPadSize
) implements io.edpn.backend.messageprocessorlib.application.dto.edpn.intermodulecommunication.StationMaxLandingPadSizeResponse {
}
