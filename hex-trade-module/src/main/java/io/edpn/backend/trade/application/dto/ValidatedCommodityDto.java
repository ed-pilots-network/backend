package io.edpn.backend.trade.application.dto;

public interface ValidatedCommodityDto {
    
    String getCommodityName();
    
    String getDisplayName();
    
    String getType();
    
    Boolean getIsRare();
}
