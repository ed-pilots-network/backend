package io.edpn.backend.trade.application.dto;


import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class FindCommodityResponse {
    LocalDateTime pricesUpdate;
    String commodityName;
    Station station;
    System system;
    
    @Value
    @Builder
    public static class Station {
        String name;
        Double arrivalDistance;
        String landingPadSize;
        Boolean planetary;
        Boolean requireOdyssey;
        Boolean fleetCarrier;
    }
    
    @Value
    @Builder
    public static class System {
        String name;
        CoordinateDTO coordinateDTO;
    }
    
}