package io.edpn.backend.trade.application.port.outgoing.locatecommodity;

import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.dto.persistence.filter.PersistenceLocateCommodityFilter;

import java.util.List;

public interface LocateCommodityByFilterPort {
    
    List<LocateCommodity> locateCommodityByFilter(PersistenceLocateCommodityFilter locateCommodityFilter);
}
