package io.edpn.backend.trade.application.dto.persistence.entity.mapper;

import io.edpn.backend.trade.application.domain.PageInfo;
import io.edpn.backend.trade.application.domain.PagedResult;
import io.edpn.backend.trade.application.dto.persistence.entity.PersistencePageInfo;

import java.util.List;
import java.util.function.Function;

import static java.util.Collections.emptyList;

public interface PersistencePagedResultMapper {

    static <T, F extends PersistencePageInfo> PagedResult<T> map(List<? extends F> entities, Function<F, T> entityMapper, Function<F, PageInfo> pageInfoMapper) {
        return entities.stream().findAny()
                .map(entity -> new PagedResult<>(
                        entities.stream()
                                .map(entityMapper)
                                .toList(),
                        pageInfoMapper.apply(entity)))
                .orElse(new PagedResult<>(
                        emptyList(),
                        PageInfo.builder()
                                .totalItems(0)
                                .currentPage(0)
                                .pageSize(0)
                                .build()));


    }
}
