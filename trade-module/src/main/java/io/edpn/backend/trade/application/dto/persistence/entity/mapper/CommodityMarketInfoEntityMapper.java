package io.edpn.backend.trade.application.dto.persistence.entity.mapper;

import io.edpn.backend.trade.application.domain.CommodityMarketInfo;
import io.edpn.backend.trade.application.dto.persistence.entity.CommodityMarketInfoEntity;

public interface CommodityMarketInfoEntityMapper<T extends CommodityMarketInfoEntity> {
    
    CommodityMarketInfo map(CommodityMarketInfoEntity commodityMarketInfoEntity);
    
    T map(CommodityMarketInfo commodityMarketInfo);
    
}
