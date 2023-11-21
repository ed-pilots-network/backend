package io.edpn.backend.trade.application.domain;

import java.util.UUID;

public record Commodity(
        UUID id,
        String name
) {
}
