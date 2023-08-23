package io.edpn.backend.trade.application.dto.persistence.filter.mapper;

import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.PersistenceLocateCommodityFilter;

public interface PersistenceLocateCommodityFilterMapper {
    
    PersistenceLocateCommodityFilter map(LocateCommodityFilter locateCommodityFilter);
}
