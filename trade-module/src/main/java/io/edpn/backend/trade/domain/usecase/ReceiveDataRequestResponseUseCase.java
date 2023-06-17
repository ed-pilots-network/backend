package io.edpn.backend.trade.domain.usecase;

public interface ReceiveDataRequestResponseUseCase<T> {
    void receive(T message);
}
