package io.edpn.backend.trade.domain.repository;

import io.edpn.backend.trade.domain.model.RequestDataMessage;

public interface RequestDataMessageRepository {

    void sendToKafka(RequestDataMessage requestDataMessage);

}
