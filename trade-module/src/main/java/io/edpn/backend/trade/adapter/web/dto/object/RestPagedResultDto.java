package io.edpn.backend.trade.adapter.web.dto.object;

import io.edpn.backend.trade.application.dto.web.object.PagedResultDto;

import java.util.List;

public record RestPagedResultDto<T>(
        List<T> result,
        RestPageInfoDto pageInfo
) implements PagedResultDto<T> {
}
