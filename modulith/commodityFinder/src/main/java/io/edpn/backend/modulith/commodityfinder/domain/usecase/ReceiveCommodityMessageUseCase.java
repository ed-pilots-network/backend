package io.edpn.backend.modulith.commodityfinder.domain.usecase;

import io.edpn.backend.modulith.messageprocessorlib.application.dto.eddn.CommodityMessage;

public interface ReceiveCommodityMessageUseCase {
    void receive(CommodityMessage.V3 message);
}
