package io.edpn.backend.trade.domain.service;

public interface RequestDataService<T> {

    boolean isApplicable(T t);

    void request(T t);
}
