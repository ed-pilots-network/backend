package io.eddb.eddb2backend.infrastructure.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface EddnMessageProcessor<T> {

    void listen(JsonNode json) throws JsonProcessingException;

    void handle(T message);

    T processJson(JsonNode json) throws JsonProcessingException;
}
