package io.edpn.backend.trade.application.port.incomming.kafka;

public interface ReceiveKafkaMessageUseCase<T> {
    void receive(T message);
}