package io.edpn.backend.trade.application.domain;

public record FindCommodityFilter(
        String type,
        Boolean isRare
) {
}
