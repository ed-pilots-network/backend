package io.edpn.backend.commodityfinder.domain.repository;

import io.edpn.backend.commodityfinder.domain.model.RequestDataMessage;

public interface RequestDataMessageRepository {

    void sendToKafka(RequestDataMessage requestDataMessage);

}
