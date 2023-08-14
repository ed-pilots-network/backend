package io.edpn.backend.trade.application.dto.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface StationEntity {
    
    UUID getId();
    
    Long getMarketId();
    
    String getName();
    
    Double getArrivalDistance();
    
    SystemEntity getSystem();
    
    Boolean getPlanetary();
    
    Boolean getRequireOdyssey();
    
    Boolean getFleetCarrier();
    
    String getMaxLandingPadSize();
    
    LocalDateTime getMarketUpdatedAt();
    
    List<MarketDatumEntity> getMarketData();
}
