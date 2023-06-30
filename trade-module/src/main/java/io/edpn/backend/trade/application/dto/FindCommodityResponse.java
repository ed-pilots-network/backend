package io.edpn.backend.trade.application.dto;

import io.edpn.backend.trade.domain.model.LandingPadSize;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class FindCommodityResponse {
    LandingPadSize landingPadSize;
    double arrivalDistance;
    LocalDateTime PricesUpdate;
    double distanceToSol;
    
    @Value
    @Builder
    public static class Station {
        
        String name;
        Double arrivalDistance;
        System system;
    }
    
    @Value
    @Builder
    public static class System {
        
        String name;
        CoordinateDTO coordinateDTO;
    }
    
}
