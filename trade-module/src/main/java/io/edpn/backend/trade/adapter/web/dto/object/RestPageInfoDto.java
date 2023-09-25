package io.edpn.backend.trade.adapter.web.dto.object;

import io.edpn.backend.trade.application.dto.web.object.PageInfoDto;

public record RestPageInfoDto(
        int pageSize,
        int currentPage,
        int totalItems
) implements PageInfoDto {
}
