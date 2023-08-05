package io.edpn.backend.trade.application.domain;

public record ValidatedCommodity(
        String commodityName,
        String displayName,
        CommodityType type,
        Boolean isRare
) {
}
