package io.edpn.backend.trade.application.dto.web.object;

import java.util.UUID;

public interface SystemDto {
    
    UUID id();
    
    String name();
    
    Long eliteId();
    
    Double xCoordinate();
    
    Double yCoordinate();
    
    Double zCoordinate();
}
