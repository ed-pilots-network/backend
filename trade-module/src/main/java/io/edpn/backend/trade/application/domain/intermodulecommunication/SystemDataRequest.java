package io.edpn.backend.trade.application.domain.intermodulecommunication;

import io.edpn.backend.util.Module;

public record SystemDataRequest(
        Module requestingModule,
        String systemName
) implements io.edpn.backend.messageprocessorlib.application.dto.edpn.intermodulecommunication.SystemDataRequest {
}
