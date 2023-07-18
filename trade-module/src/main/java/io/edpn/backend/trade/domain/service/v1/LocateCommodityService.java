package io.edpn.backend.trade.domain.service.v1;

import io.edpn.backend.trade.application.dto.v1.LocateCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityResponse;

import java.util.List;

public interface LocateCommodityService {
    
    List<LocateCommodityResponse> locateCommoditiesOrderByDistance(LocateCommodityRequest locateCommodityRequest);
}
