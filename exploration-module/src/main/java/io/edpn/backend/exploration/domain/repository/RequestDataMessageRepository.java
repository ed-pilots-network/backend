package io.edpn.backend.exploration.domain.repository;

import io.edpn.backend.exploration.domain.model.RequestDataMessage;

public interface RequestDataMessageRepository {
    void sendToKafka(RequestDataMessage requestDataMessage);
}
