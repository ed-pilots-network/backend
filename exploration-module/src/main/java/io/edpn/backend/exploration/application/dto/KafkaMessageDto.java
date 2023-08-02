package io.edpn.backend.exploration.application.dto;

public interface KafkaMessageDto {
    String topic();

    String message();
}
