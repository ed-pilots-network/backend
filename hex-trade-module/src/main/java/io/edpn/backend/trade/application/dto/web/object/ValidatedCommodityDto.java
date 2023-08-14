package io.edpn.backend.trade.application.dto.web.object;

public interface ValidatedCommodityDto {
    
    String getCommodityName();
    
    String getDisplayName();
    
    String getType();
    
    Boolean getIsRare();
}
