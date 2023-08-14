package io.edpn.backend.trade.application.dto.persistence.filter;

import io.edpn.backend.trade.application.domain.LandingPadSize;

public interface PersistenceLocateCommodityFilter {
    
    String getCommodityDisplayName();
    
    Double getXCoordinate();
    
    Double getYCoordinate();
    
    Double getZCoordinate();
    
    Boolean getIncludePlanetary();
    
    Boolean getIncludeOdyssey();
    
    Boolean getIncludeFleetCarriers();
    
    String getMaxLandingPadSize();
    
    Long getMinSupply();
    
    Long getMinDemand();
}
