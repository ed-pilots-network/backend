package io.edpn.backend.trade.domain.usecase;

import io.edpn.backend.trade.application.dto.FindCommodityRequest;
import io.edpn.backend.trade.domain.model.MarketDatum;

import java.util.List;
import java.util.UUID;

public interface FindCommodityUseCase {
    
    List<MarketDatum> findCommoditiesOrderByDistance(UUID uuid);
    
}
