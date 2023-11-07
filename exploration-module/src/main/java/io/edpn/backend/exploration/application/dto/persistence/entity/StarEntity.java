package io.edpn.backend.exploration.application.dto.persistence.entity;

import java.util.List;
import java.util.UUID;

public interface StarEntity {
    UUID getId();
    
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
    
    List<? extends RingEntity> getRings();
    
    Double getRotationalPeriod();
    
    String getStarType();
    
    Long getStellarMass(); // in multiples of Sol
    
    Integer getSubclass();
    
    Double getSurfaceTemperature();
    
    SystemEntity getSystem();
    
    Long getSystemAddress();
    
    Boolean getHorizons();
    
    Boolean getOdyssey();
    
    Double getEstimatedScanValue();
}
    
