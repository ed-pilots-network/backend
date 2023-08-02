package io.edpn.backend.exploration.adapter.kafka.dto;

public record KafkaMessageDto(String topic,
                              String message) implements io.edpn.backend.exploration.application.dto.KafkaMessageDto {
}
