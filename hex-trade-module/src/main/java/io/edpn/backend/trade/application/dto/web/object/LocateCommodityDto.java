package io.edpn.backend.trade.application.dto.web.object;

import io.edpn.backend.trade.application.domain.Station;

import java.time.LocalDateTime;

public interface LocateCommodityDto {
    
    String getValidatedCommodityDisplayName();
    
    Station getStation();
    
    String getSystemName();
    
    LocalDateTime getPricesUpdatedAt();
    
    Long getSupply();
    
    Long getDemand();
    
    Long getBuyPrice();
    
    Long getSellPrice();
    
    Double getDistance();
}
