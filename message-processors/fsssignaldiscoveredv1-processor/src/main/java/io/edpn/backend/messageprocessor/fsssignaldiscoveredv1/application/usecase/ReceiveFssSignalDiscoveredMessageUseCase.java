package io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.application.usecase;

import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.application.dto.eddn.FssSignalDiscoveredMessage;

public interface ReceiveFssSignalDiscoveredMessageUseCase {

    void receive(FssSignalDiscoveredMessage.V1 message);
}
