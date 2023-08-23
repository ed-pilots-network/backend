package io.edpn.backend.trade.application.dto.web.object;

import java.time.LocalDateTime;

public interface StationDto {
    
    Long marketId();
    
    String name();
    
    Double arrivalDistance();
    
    SystemDto system();
    
    Boolean planetary();
    
    Boolean requireOdyssey();
    
    Boolean fleetCarrier();
    
    String maxLandingPadSize();
    
    LocalDateTime marketUpdatedAt();
}
