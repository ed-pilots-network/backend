package io.edpn.backend.trade.application.dto.web.filter;

public interface LocateCommodityFilterDto {
    
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
