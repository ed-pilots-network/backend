package io.edpn.backend.commodityfinder.domain.usecase;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.ApproachSettlement;

public interface ReceiveApproachSettlementMessageUseCase {
    void receive(ApproachSettlement.V1 message);
}
