package io.edpn.backend.trade.application.dto.persistence.filter;

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
