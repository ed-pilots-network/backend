package io.edpn.backend.exploration.domain.repository;

import io.edpn.backend.exploration.domain.model.RequestDataMessage;

import java.util.List;
import java.util.Optional;

public interface RequestDataMessageRepository {
    Optional<RequestDataMessage> find(RequestDataMessage requestDataMessage);

    void create(RequestDataMessage requestDataMessage);

    List<RequestDataMessage> findNotSend();

    void setSend(RequestDataMessage requestDataMessage);
}
