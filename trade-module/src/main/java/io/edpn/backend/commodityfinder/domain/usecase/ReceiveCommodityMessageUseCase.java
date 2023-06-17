package io.edpn.backend.commodityfinder.domain.usecase;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.CommodityMessage;

public interface ReceiveCommodityMessageUseCase {
    void receive(CommodityMessage.V3 message);
}
