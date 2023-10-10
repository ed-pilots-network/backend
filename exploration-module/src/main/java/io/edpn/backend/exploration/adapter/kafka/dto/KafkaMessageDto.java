package io.edpn.backend.exploration.adapter.kafka.dto;

import io.edpn.backend.exploration.application.dto.web.object.MessageDto;

public record KafkaMessageDto(String topic,
                              String message) implements MessageDto {
}
