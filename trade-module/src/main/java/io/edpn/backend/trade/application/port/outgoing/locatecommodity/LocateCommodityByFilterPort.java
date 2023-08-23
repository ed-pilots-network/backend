package io.edpn.backend.trade.application.port.outgoing.locatecommodity;

import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;

import java.util.List;

public interface LocateCommodityByFilterPort {
    
    List<LocateCommodity> locateCommodityByFilter(LocateCommodityFilter locateCommodityFilter);
}
