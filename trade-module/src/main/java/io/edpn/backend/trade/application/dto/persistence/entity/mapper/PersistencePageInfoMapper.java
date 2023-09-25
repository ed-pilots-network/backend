package io.edpn.backend.trade.application.dto.persistence.entity.mapper;

import io.edpn.backend.trade.application.domain.PageInfo;
import io.edpn.backend.trade.application.dto.persistence.entity.PersistencePageInfo;

public interface PersistencePageInfoMapper {
    PageInfo map(PersistencePageInfo persistencePageInfo);
}
