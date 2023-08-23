package io.edpn.backend.trade.application.dto.web.object;

public interface ValidatedCommodityDto {
    
    String commodityName();
    
    String displayName();
    
    String type();
    
    Boolean isRare();
}
