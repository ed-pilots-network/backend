package io.edpn.backend.trade.domain.service;

import io.edpn.backend.trade.application.dto.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.FindCommodityResponse;

import java.util.List;

public interface FindCommodityService {
    
    List<FindCommodityResponse> findCommoditiesNearby(FindCommodityRequest findCommodityRequest);
}
