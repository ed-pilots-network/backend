package io.edpn.backend.exploration.domain.usecase;

public interface ReceiveDataRequestUseCase<T> {
    void receive(T message);
}
