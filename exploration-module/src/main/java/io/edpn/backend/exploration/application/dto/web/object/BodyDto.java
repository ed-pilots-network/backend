package io.edpn.backend.exploration.application.dto.web.object;

import java.util.List;
import java.util.Map;

public interface BodyDto {
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
    
    Map<Integer, String> getParents(); // ID's are system local
    
    Double getPeriapsis();
    
    String getPlanetClass();
    
    Double getRadius();
    
    List<RingDto> getRings();
    
    Double getRotationPeriod(); // seconds
    
    Double getSemiMajorAxis();
    
    Double getSurfaceGravity();
    
    Double getSurfacePressure();
    
    Double getSurfaceTemperature();
    
    SystemDto getSystemDto();
    
    Long getSystemAddress();
    
    String getTerraformState();
    
    Boolean getTidalLock();
    
    String getVolcanism();
    
    Boolean getHorizons();
    
    Boolean getOdyssey();
    
    Double getEstimatedScanValue();
}
