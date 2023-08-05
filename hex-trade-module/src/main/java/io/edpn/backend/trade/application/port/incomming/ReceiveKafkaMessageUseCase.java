package io.edpn.backend.trade.application.port.incomming;

public interface ReceiveKafkaMessageUseCase<T> {
    void receive(T message);
}