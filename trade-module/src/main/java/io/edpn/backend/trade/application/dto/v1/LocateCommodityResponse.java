package io.edpn.backend.trade.application.dto.v1;


import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class LocateCommodityResponse {
    LocalDateTime pricesUpdatedAt;
    String commodityName;
    Station station;
    System system;
    Long supply;
    Long demand;
    Long buyPrice;
    Long sellPrice;
//    Double distance;
    
    @Value
    @Builder
    public static class Station {
        String name;
        Double arrivalDistance;
        String maxLandingPadSize;
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
