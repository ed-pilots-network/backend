package io.edpn.backend.trade.domain.service;

public interface KafkaSenderService<T> {
    void sendToKafka(T message);
}
