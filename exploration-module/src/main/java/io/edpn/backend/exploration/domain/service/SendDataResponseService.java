package io.edpn.backend.exploration.domain.service;

public interface SendDataResponseService<T> {
    void send(T data, String requestingModule);
}
