package io.edpn.backend.commodityfinder.domain.usecase;

public interface ReceiveDataRequestResponseUseCase<T> {
    void receive(T message);
}
