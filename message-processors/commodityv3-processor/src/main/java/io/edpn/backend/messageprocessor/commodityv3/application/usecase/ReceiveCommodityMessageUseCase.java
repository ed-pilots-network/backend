package io.edpn.backend.messageprocessor.commodityv3.application.usecase;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.eddn.CommodityMessage;

public interface ReceiveCommodityMessageUseCase {

    void receive(CommodityMessage.V3 message);
}
