package io.edpn.backend.exploration.application.dto.persistence.entity;

import java.util.UUID;

public interface RingEntity {
    UUID getId();
    
    Long getInnerRadius();
    
    Long getMass(); // MT MegaTonnes?
    
    String getName();
    
    Long getOuterRadius();
    
    String getRingClass();
}
