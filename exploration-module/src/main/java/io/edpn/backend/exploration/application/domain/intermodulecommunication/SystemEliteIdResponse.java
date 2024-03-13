package io.edpn.backend.exploration.application.domain.intermodulecommunication;

import io.edpn.backend.exploration.application.domain.System;

public record SystemEliteIdResponse(

        String systemName,
        long eliteId
) implements io.edpn.backend.messageprocessorlib.application.dto.edpn.intermodulecommunication.SystemEliteIdResponse {
    public static SystemEliteIdResponse from(System system) {

        return new SystemEliteIdResponse(
                system.name(),
                system.eliteId());
    }
}
