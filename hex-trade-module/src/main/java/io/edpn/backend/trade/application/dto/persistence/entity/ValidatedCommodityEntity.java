package io.edpn.backend.trade.application.dto.persistence.entity;

import java.util.UUID;

public interface ValidatedCommodityEntity {
    UUID getId();
    
    String getCommodityName();
    
    String getDisplayName();
    
    String getType();
    
    Boolean getIsRare();
}
