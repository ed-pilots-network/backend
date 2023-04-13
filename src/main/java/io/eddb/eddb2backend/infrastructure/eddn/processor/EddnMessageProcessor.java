package io.eddb.eddb2backend.infrastructure.eddn.processor;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface EddnMessageProcessor<T> {

    default void handle(String json) throws JsonProcessingException {
        handle(processJson(json));
    }

    void handle(T message);

    T processJson(String json) throws JsonProcessingException;
}
