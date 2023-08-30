package io.edpn.backend.trade.adapter.web.dto.filter;

import io.edpn.backend.trade.application.dto.web.filter.PageFilterDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PageFilterDto")
public record RestPageFilterDto(
        int size,
        int page
) implements PageFilterDto {
}
