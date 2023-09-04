package io.edpn.backend.trade.application.dto.web.object.mapper;

import io.edpn.backend.trade.application.domain.PageInfo;
import io.edpn.backend.trade.application.domain.PagedResult;
import io.edpn.backend.trade.application.dto.web.object.PageInfoDto;
import io.edpn.backend.trade.application.dto.web.object.PagedResultDto;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface PagedResultDtoMapper {
    static <T, F, Z extends PageInfoDto, U extends PagedResultDto<F>> PagedResultDto<F> map(
            PagedResult<T> pagedResult,
            Function<T, F> dtoMapper,
            Function<PageInfo, Z> pageInfoMapper,
            BiFunction<List<F>, Z, U> initializer
    ) {
        Z pageInfoDto = pageInfoMapper.apply(pagedResult.pageInfo());
        List<F> list = pagedResult.result().stream().map(dtoMapper).toList();
        return initializer.apply(list, pageInfoDto);
    }
}
