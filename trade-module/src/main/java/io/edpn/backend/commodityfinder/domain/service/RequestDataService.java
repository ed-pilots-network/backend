package io.edpn.backend.commodityfinder.domain.service;

public interface RequestDataService<T> {

    boolean isApplicable(T t);

    void request(T t);
}