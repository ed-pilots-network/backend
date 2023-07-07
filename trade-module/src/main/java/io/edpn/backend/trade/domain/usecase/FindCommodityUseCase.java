package io.edpn.backend.trade.domain.usecase;

import io.edpn.backend.trade.application.dto.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.FindCommodityResponse;
import io.edpn.backend.trade.domain.model.FindCommodity;
import io.edpn.backend.trade.domain.model.FindCommodityFilter;
import io.edpn.backend.trade.domain.model.MarketDatum;

import java.util.List;
import java.util.UUID;

public interface FindCommodityUseCase {
    
    List<FindCommodity> findCommoditiesOrderByDistance(FindCommodityFilter findCommodityFilter);
    
}
