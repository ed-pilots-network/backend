package io.edpn.backend.trade.domain.service;

public interface SendDataResponseService<T> {
    void send(T data, String requestingModule);
}
