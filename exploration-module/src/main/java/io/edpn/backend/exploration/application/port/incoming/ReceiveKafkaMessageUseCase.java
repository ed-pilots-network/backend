package io.edpn.backend.exploration.application.port.incoming;

public interface ReceiveKafkaMessageUseCase<T> {
    void receive(T message);
}
