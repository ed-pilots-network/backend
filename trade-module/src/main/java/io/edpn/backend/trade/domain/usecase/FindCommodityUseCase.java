package io.edpn.backend.trade.domain.usecase;

import io.edpn.backend.trade.domain.model.FindCommodity;
import io.edpn.backend.trade.domain.filter.FindCommodityFilter;

import java.util.List;

public interface FindCommodityUseCase {
    
    List<FindCommodity> findCommoditiesOrderByDistance(FindCommodityFilter findCommodityFilter);
    
}
