package io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface MessageProcessor<T> {

    void listen(JsonNode json) throws JsonProcessingException;

    void handle(T message);

    T processJson(JsonNode json) throws JsonProcessingException;
}
