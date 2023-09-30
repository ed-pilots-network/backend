package io.edpn.backend.trade.application.dto.persistence.filter;

public interface PersistenceFindSystemFilter {
    
    String getName();
    
    Boolean getHasEliteId();
    Boolean getHasCoordinates();
}
