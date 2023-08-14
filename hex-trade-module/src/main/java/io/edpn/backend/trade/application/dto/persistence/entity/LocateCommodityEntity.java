package io.edpn.backend.trade.application.dto.persistence.entity;

import java.time.LocalDateTime;

public interface LocateCommodityEntity {
    
    ValidatedCommodityEntity getValidatedCommodity();
    
    StationEntity getStation();
    
    SystemEntity getSystem();
    
    LocalDateTime getPricesUpdatedAt();
    
    Long getSupply();
    
    Long getDemand();
    
    Long getBuyPrice();
    
    Long getSellPrice();
    
    Double getDistance();
}
