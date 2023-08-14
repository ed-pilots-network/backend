package io.edpn.backend.trade.application.dto.web.object;

import java.time.LocalDateTime;

//TODO: Add implementation
public interface StationDto {
    
    Long getMarketId();
    
    String getName();
    
    Double getArrivalDistance();
    
    //TODO: SystemDto or name?
    String getSystemName();
    
    Boolean getPlanetary();
    
    Boolean getRequireOdyssey();
    
    Boolean getFleetCarrier();
    
    String getMaxLandingPadSize();
    
    LocalDateTime getMarketUpdatedAt();
}
