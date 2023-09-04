package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.application.domain.PageInfo;
import io.edpn.backend.trade.application.dto.persistence.entity.PersistencePageInfo;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.PersistencePageInfoMapper;

public class MybatisPageInfoMapper implements PersistencePageInfoMapper {

    @Override
    public PageInfo map(PersistencePageInfo persistencePageInfo) {
        return PageInfo.builder()
                .pageSize(persistencePageInfo.getPageSize())
                .currentPage(persistencePageInfo.getCurrentPage())
                .totalItems(persistencePageInfo.getTotalItems())
                .build();
    }
}
