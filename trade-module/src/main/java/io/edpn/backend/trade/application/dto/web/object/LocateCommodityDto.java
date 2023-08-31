package io.edpn.backend.trade.application.dto.web.object;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LocateCommodityDto {
    
    ValidatedCommodityDto commodity();
    
    StationDto station();
    
    LocalDateTime priceUpdatedAt();
    
    Long supply();
    
    Long demand();
    
    Long buyPrice();
    
    Long sellPrice();
    
    Double distance();

    Optional<? extends PageInfoDto> pageInfo();
}
