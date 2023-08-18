package io.edpn.backend.trade.application.dto.web.object;

import java.time.LocalDateTime;

public interface LocateCommodityDto {
    
    String commodityDisplayName();
    
    StationDto station();
    
    String systemName();
    
    LocalDateTime pricesUpdatedAt();
    
    Long supply();
    
    Long demand();
    
    Long buyPrice();
    
    Long sellPrice();
    
    Double distance();
}
