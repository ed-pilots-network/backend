package io.edpn.backend.trade.adapter.kafka.dto;

public record KafkaMessageDto(
        String topic,
        String message
) {
}