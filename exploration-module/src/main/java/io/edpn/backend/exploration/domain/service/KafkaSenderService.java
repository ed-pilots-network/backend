package io.edpn.backend.exploration.domain.service;

public interface KafkaSenderService<T> {
    void sendToKafka(T message);
}
