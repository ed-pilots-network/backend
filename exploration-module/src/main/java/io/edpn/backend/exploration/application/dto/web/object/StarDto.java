package io.edpn.backend.exploration.application.dto.web.object;

import java.util.List;

public interface StarDto {
    Double getAbsoluteMagnitude();
    
    Long getAge(); // millions of years
    
    Double getArrivalDistance();// LS
    
    Boolean getDiscovered();
    
    Long getEliteId();
    
    String getLuminosity();
    
    Boolean getMapped();
    
    String getName();
    
    Long getRadius();
    
    List<RingDto> getRings();
    
    Double getRotationalPeriod();
    
    String getStarType();
    
    Long getStellarMass(); // in multiples of Sol
    
    Long getSurfaceTemperature();
    
    SystemDto getSystem();
    
    Boolean getHorizons();
    
    Boolean getOdyssey();
    
    Double getEstimatedScanValue();
}
