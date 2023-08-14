package io.edpn.backend.trade.application.dto.web.object;

import java.time.LocalDateTime;

public interface StationDto {
    
    Long getMarketId();
    
    String getName();
    
    Double getArrivalDistance();
    
    String getSystemName();
    
    Boolean getPlanetary();
    
    Boolean getRequireOdyssey();
    
    Boolean getFleetCarrier();
    
    String getMaxLandingPadSize();
    
    LocalDateTime getMarketUpdatedAt();
}
