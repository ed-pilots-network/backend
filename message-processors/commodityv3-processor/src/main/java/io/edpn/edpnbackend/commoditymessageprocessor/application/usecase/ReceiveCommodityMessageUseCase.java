package io.edpn.edpnbackend.commoditymessageprocessor.application.usecase;

import io.edpn.edpnbackend.commoditymessageprocessor.application.dto.eddn.CommodityMessage;

public interface ReceiveCommodityMessageUseCase {

    void receive(CommodityMessage.V3 message);
}
