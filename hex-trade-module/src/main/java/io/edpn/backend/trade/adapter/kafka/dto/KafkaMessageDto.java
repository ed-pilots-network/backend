package io.edpn.backend.trade.adapter.kafka.dto;

import io.edpn.backend.trade.application.dto.web.object.MessageDto;

public record KafkaMessageDto(
        String topic,
        String message
) implements MessageDto {
}