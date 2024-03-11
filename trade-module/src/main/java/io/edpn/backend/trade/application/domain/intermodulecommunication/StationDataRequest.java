package io.edpn.backend.trade.application.domain.intermodulecommunication;

import io.edpn.backend.util.Module;

public record StationDataRequest(
        Module requestingModule,
        String stationName,
        String systemName
) implements io.edpn.backend.messageprocessorlib.application.dto.edpn.intermodulecommunication.StationDataRequest {
}
