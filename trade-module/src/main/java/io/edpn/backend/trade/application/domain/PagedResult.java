package io.edpn.backend.trade.application.domain;

import java.util.List;

public record PagedResult<T>(
        List<T> result,
        PageInfo pageInfo
) {
}
