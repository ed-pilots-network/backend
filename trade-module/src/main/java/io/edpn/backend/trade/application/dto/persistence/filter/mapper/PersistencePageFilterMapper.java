package io.edpn.backend.trade.application.dto.persistence.filter.mapper;

import io.edpn.backend.trade.application.domain.filter.PageFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.PersistencePageFilter;

public interface PersistencePageFilterMapper {

    PersistencePageFilter map(PageFilter pageFilter);
}
