package io.edpn.backend.exploration.application.port.incomming;

public interface ReceiveKafkaMessageUseCase<T> {
    void receive(T message);
}
