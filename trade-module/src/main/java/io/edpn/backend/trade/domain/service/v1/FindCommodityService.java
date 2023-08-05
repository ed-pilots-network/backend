package io.edpn.backend.trade.domain.service.v1;

import io.edpn.backend.trade.application.dto.v1.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.FindCommodityResponse;

import java.util.List;
import java.util.Optional;

public interface FindCommodityService {
    
    Optional<FindCommodityResponse> findByName(String displayName);
    
    List<FindCommodityResponse> findAll();
    
    List<FindCommodityResponse> findByFilter(FindCommodityRequest findCommodityRequest);
}
