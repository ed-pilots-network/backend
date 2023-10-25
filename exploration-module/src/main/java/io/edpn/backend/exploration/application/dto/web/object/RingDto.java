package io.edpn.backend.exploration.application.dto.web.object;

import java.util.UUID;

public interface RingDto{
    Long getInnerRadius();
    
    Long getMass(); // MT MegaTonnes?
    
    String getName();
    
    Long getOuterRadius();
    
    String getRingClass();
    
    UUID getBodyId();
    
    UUID getStarId();
}
