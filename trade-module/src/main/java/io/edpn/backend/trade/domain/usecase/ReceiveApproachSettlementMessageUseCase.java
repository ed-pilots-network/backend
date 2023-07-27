package io.edpn.backend.trade.domain.usecase;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.ApproachSettlementMessage;

public interface ReceiveApproachSettlementMessageUseCase {
    void receive(ApproachSettlementMessage.V1 message);
}
