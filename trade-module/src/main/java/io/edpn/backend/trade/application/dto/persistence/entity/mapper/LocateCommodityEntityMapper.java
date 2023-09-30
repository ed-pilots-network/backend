package io.edpn.backend.trade.application.dto.persistence.entity.mapper;

import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.dto.persistence.entity.LocateCommodityEntity;

public interface LocateCommodityEntityMapper<T extends LocateCommodityEntity> {
    
    LocateCommodity map(LocateCommodityEntity locateCommodityEntity);
    
    T map(LocateCommodity locateCommodity);
}
