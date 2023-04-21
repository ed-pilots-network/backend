package io.eddb.eddb2backend.application.usecase;

import io.eddb.eddb2backend.application.dto.eddn.CommodityMessage;

public interface ReceiveCommodityMessageUseCase {

    void receive(CommodityMessage.V3 message);
}
