package io.edpn.backend.messageprocessor.navroutev1.application.usecase;

import io.edpn.backend.messageprocessor.navroutev1.application.dto.eddn.NavRouteMessage;

public interface ReceiveNavRouteMessageUseCase {

    void receive(NavRouteMessage.V1 message);
}
