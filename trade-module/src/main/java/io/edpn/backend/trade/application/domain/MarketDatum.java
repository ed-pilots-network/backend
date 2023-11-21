package io.edpn.backend.trade.application.domain;

import lombok.With;

import java.time.LocalDateTime;
import java.util.List;


@With
public record MarketDatum(
        Commodity commodity,
        LocalDateTime timestamp,
        long meanPrice,
        long buyPrice,
        long stock,
        long stockBracket,
        long sellPrice,
        long demand,
        long demandBracket,
        List<String> statusFlags,
        boolean prohibited
) {
}
