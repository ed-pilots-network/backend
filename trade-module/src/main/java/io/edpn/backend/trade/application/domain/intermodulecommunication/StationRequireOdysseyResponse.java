package io.edpn.backend.trade.application.domain.intermodulecommunication;

public record StationRequireOdysseyResponse(
        String stationName,
        String systemName,
        boolean requireOdyssey
) implements io.edpn.backend.messageprocessorlib.application.dto.edpn.intermodulecommunication.StationRequireOdysseyResponse {
}
