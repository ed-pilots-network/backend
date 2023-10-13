package io.edpn.backend.exploration.application.dto.persistence.entity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BodyEntity {
    UUID getId();
    
    Double getArrivalDistance(); // LS
    
    Double getAscendingNode();
    
    String getAtmosphere();
    
    Map<String, Double> getAtmosphericComposition();
    
    Double getAxialTilt();
    
    Map<String, Double> getBodyComposition();
    
    Boolean getDiscovered();
    
    Boolean getMapped();
    
    String getName();
    
    Long getLocalId();
    
    Double getEccentricity();
    
    Boolean getLandable();
    
    Double getMass();
    
    Map<String, Double> getMaterials();
    
    Double getMeanAnomaly();
    
    Double getOrbitalInclination();
    
    Double getOrbitalPeriod();
    
    Map<String, Integer> getParents(); // ID's are system local
    
    Double getPeriapsis();
    
    String getPlanetClass();
    
    Double getRadius();
    
    List<RingEntity> getRings();
    
    Double getRotationPeriod(); // seconds
    
    Double getSemiMajorAxis();
    
    Double getSurfaceGravity();
    
    Double getSurfacePressure();
    
    Double getSurfaceTemperature();
    
    SystemEntity getSystem();
    
    Long getSystemAddress();
    
    String getTerraformState();
    
    Boolean getTidalLock();
    
    String getVolcanism();
    
    Boolean getHorizons();
    
    Boolean getOdyssey();
    
    Double getEstimatedScanValue();
}
