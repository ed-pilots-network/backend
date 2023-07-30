package io.edpn.backend.exploration.application.port.incoming;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.ApproachSettlementMessage;

public interface ReceiveApproachSettlementMessageUseCase {
    void receive(ApproachSettlementMessage.V1 message);
}
