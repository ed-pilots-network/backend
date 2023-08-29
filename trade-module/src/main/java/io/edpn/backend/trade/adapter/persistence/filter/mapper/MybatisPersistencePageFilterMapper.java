package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.MybatisPageFilter;
import io.edpn.backend.trade.application.domain.filter.PageFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.PersistencePageFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistencePageFilterMapper;

public class MybatisPersistencePageFilterMapper implements PersistencePageFilterMapper {
    @Override
    public PersistencePageFilter map(PageFilter pageFilter) {
        return MybatisPageFilter.builder()
                .size(pageFilter.getSize())
                .page(pageFilter.getPage())
                .build();
    }
}
