package io.edpn.backend.trade.application.domain.intermodulecommunication;

public record SystemEliteIdResponse(

        String systemName,
        long eliteId
) implements io.edpn.backend.messageprocessorlib.application.dto.edpn.intermodulecommunication.SystemEliteIdResponse {
}
