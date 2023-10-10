package io.edpn.backend.exploration.application.dto.persistence.entity;

public interface RingEntity {
    Long getInnerRadius();
    
    Long getMass(); // MT MegaTonnes?
    
    String getName();
    
    Long getOuterRadius();
    
    String getRingClass();
}
