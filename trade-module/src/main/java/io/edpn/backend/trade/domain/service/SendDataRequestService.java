package io.edpn.backend.trade.domain.service;

public interface SendDataRequestService<T> {
    void send(T data, String topic);
}
