package io.edpn.backend.trade.adapter.web.dto.filter;

import io.edpn.backend.trade.application.dto.web.filter.FindCommodityFilterDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FindCommodityFilterDto")
public record RestFindCommodityFilterDto(
        String type,
        Boolean isRare
) implements FindCommodityFilterDto {
}
