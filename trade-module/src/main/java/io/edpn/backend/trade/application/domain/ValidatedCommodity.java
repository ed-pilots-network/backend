package io.edpn.backend.trade.application.domain;

import java.util.UUID;


public record ValidatedCommodity(
        UUID id,
        String commodityName,
        String displayName,
        CommodityType type,
        Boolean isRare
) {
}
