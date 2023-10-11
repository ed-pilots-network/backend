package io.edpn.backend.exploration.application.dto.persistence.entity;

import io.edpn.backend.exploration.application.dto.web.object.SystemDto;

import java.util.List;
import java.util.UUID;

public interface StarEntity {
    UUID getId();
    
    Double getAbsoluteMagnitude();
    
    Long getAge(); // millions of years
    
    Double getArrivalDistance();// LS
    
    Boolean getDiscovered();
    
    Long getEliteId();
    
    String getLuminosity();
    
    Boolean getMapped();
    
    String getName();
    
    Long getRadius();
    
    List<RingEntity> getRings();
    
    Double getRotationalPeriod();
    
    String getStarType();
    
    Long getStellarMass(); // in multiples of Sol
    
    Long getSurfaceTemperature();
    
    SystemDto getSystem();
    
    Boolean getHorizons();
    
    Boolean getOdyssey();
    
    Double getEstimatedScanValue();
}
