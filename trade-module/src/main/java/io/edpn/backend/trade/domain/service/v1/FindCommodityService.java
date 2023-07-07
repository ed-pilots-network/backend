package io.edpn.backend.trade.domain.service.v1;

import io.edpn.backend.trade.application.dto.v1.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.FindCommodityResponse;

import java.util.List;

public interface FindCommodityService {
    
    List<FindCommodityResponse> findCommoditiesNearby(FindCommodityRequest findCommodityRequest);
}
