package io.edpn.backend.trade.adapter.web.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FindCommodityFilterDto")
public record RestFindCommodityFilterDto(
        String type,
        Boolean isRare
) {
}
