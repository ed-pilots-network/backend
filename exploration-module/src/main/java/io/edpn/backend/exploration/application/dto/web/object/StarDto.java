package io.edpn.backend.exploration.application.dto.web.object;

import java.util.List;

public interface StarDto {
    Double getAbsoluteMagnitude();
    
    Long getAge(); // millions of years
    
    Double getArrivalDistance();// LS
    
    Double getAxialTilt();
    
    Boolean getDiscovered();
    
    Long getLocalId();
    
    String getLuminosity();
    
    Boolean getMapped();
    
    String getName();
    
    Double getRadius();
    
    List<RingDto> getRings();
    
    Double getRotationalPeriod();
    
    String getStarType();
    
    Long getStellarMass(); // in multiples of Sol
    
    Long getSubclass();
    
    Double getSurfaceTemperature();
    
    SystemDto getSystem();
    
    Long getSystemAddress();
    
    Boolean getHorizons();
    
    Boolean getOdyssey();
    
    Double getEstimatedScanValue();
}