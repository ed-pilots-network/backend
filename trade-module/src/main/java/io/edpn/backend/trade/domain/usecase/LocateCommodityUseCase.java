package io.edpn.backend.trade.domain.usecase;

import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.domain.filter.v1.LocateCommodityFilter;

import java.util.List;

public interface LocateCommodityUseCase {
    
    List<LocateCommodity> locateCommoditiesOrderByDistance(LocateCommodityFilter locateCommodityFilter);
    
}
