package io.edpn.backend.trade.application.domain;

public record Message(
        String topic,
        String message
) {
}
