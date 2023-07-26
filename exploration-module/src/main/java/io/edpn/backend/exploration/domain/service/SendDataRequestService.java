package io.edpn.backend.exploration.domain.service;

public interface SendDataRequestService<T> {
    void send(T data, String topic);
}
