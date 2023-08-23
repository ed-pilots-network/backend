package io.edpn.backend.trade.application.dto.persistence.entity;

import java.util.UUID;

public interface SystemEntity {
    
    UUID getId();
    
    String getName();
    
    Long getEliteId();
    
    Double getXCoordinate();
    
    Double getYCoordinate();
    
    Double getZCoordinate();
}
