package io.edpn.backend.trade.application.port.outgoing.locatecommodity;

import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.PagedResult;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;

public interface LocateCommodityByFilterPort {

    PagedResult<LocateCommodity> locateCommodityByFilter(LocateCommodityFilter locateCommodityFilter);
}
