package io.edpn.backend.trade.application.port.incomming.kafka;

public interface RequestDataUseCase<T> {

    boolean isApplicable(T t);

    void request(T t);
}
