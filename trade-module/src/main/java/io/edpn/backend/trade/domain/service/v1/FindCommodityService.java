package io.edpn.backend.trade.domain.service.v1;

import io.edpn.backend.trade.application.dto.v1.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.FindCommodityResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FindCommodityService {
    
    Optional<FindCommodityResponse> findById(UUID id);
    
    List<FindCommodityResponse> findAll();
    
    List<FindCommodityResponse> findByFilter(FindCommodityRequest findCommodityRequest);
}
