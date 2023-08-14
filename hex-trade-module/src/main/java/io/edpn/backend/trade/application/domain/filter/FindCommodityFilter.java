package io.edpn.backend.trade.application.domain.filter;

public record FindCommodityFilter(
        String type,
        Boolean isRare
) {
}
