package io.edpn.backend.trade.adapter.web.dto.filter;

import io.edpn.backend.trade.application.dto.web.filter.FindCommodityFilterDto;

public record RestFindCommodityFilterDto(
        String type,
        Boolean isRare
) implements FindCommodityFilterDto {
}
