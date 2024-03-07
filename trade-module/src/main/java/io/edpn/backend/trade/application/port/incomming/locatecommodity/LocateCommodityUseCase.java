package io.edpn.backend.trade.application.port.incomming.locatecommodity;

import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;

import java.util.List;

public interface LocateCommodityUseCase {

    List<LocateCommodity> locateCommodityOrderByDistance(LocateCommodityFilter locateCommodityFilter);

}
