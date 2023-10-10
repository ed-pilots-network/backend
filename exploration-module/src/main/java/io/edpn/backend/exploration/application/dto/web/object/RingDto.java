package io.edpn.backend.exploration.application.dto.web.object;

public interface RingDto{
    Long getInnerRadius();
    
    Long getMass(); // MT MegaTonnes?
    
    String getName();
    
    Long getOuterRadius();
    
    String getRingClass();
}
