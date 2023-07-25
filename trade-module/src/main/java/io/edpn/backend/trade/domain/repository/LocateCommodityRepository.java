package io.edpn.backend.trade.domain.repository;

import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.domain.filter.v1.LocateCommodityFilter;

import java.util.List;

public interface LocateCommodityRepository {
    
    List<LocateCommodity> locateCommodityByFilter(LocateCommodityFilter locateCommodityFilter);
}
