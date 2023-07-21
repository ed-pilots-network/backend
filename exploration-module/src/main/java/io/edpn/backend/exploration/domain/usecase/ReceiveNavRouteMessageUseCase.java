package io.edpn.backend.exploration.domain.usecase;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;

public interface ReceiveNavRouteMessageUseCase {
    void receive(NavRouteMessage.V1 message);
}
