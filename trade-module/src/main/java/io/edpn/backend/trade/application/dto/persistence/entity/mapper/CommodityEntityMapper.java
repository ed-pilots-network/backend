package io.edpn.backend.trade.application.dto.persistence.entity.mapper;

import io.edpn.backend.trade.application.domain.Commodity;
import io.edpn.backend.trade.application.dto.persistence.entity.CommodityEntity;

public interface CommodityEntityMapper<T extends CommodityEntity> {
    
    Commodity map(CommodityEntity commodityEntity);
    
    T map(Commodity commodity);
}
