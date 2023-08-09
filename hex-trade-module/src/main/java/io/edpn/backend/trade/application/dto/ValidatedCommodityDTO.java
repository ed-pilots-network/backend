package io.edpn.backend.trade.application.dto;

public interface ValidatedCommodityDTO {
    
    String getCommodityName();
    
    String getDisplayName();
    
    String getType();
    
    Boolean getIsRare();
}
