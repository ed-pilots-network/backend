package io.edpn.backend.trade.adapter.web.dto.filter;

import io.edpn.backend.trade.application.dto.web.filter.PageFilterDto;

public record RestPageFilterDto(
        int size,
        int page
) implements PageFilterDto {
}
