package io.edpn.backend.trade.application.dto.persistence.filter;

public interface PersistenceFindStationFilter {
    
    Boolean getHasRequiredOdyssey();
    Boolean getHasArrivalDistance();
}
