package io.edpn.backend.trade.application.dto.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;

public interface MarketDatumEntity {
    
    CommodityEntity getCommodity();
    
    LocalDateTime getTimestamp();
    
    long getMeanPrice();
    
    long getBuyPrice();
    
    long getStock();
    
    long getStockBracket();
    
    long getSellPrice();
    
    long getDemand();
    
    long getDemandBracket();
    
    List<String> getStatusFlags();
    
    boolean isProhibited();
}
