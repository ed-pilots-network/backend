package io.edpn.edpnbackend.application.usecase;

import io.edpn.edpnbackend.application.dto.eddn.CommodityMessage;

public interface ReceiveCommodityMessageUseCase {

    void receive(CommodityMessage.V3 message);
}
