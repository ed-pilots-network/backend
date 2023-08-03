package io.edpn.backend.exploration.application.domain;

public record KafkaMessage(String topic,
                           String message) {
}
