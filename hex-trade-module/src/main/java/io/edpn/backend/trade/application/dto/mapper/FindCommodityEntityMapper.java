package io.edpn.backend.trade.application.dto.mapper;

import io.edpn.backend.trade.application.dto.FindCommodityDTO;
import io.edpn.backend.trade.application.dto.FindCommodityEntity;

public interface FindCommodityEntityMapper<T extends FindCommodityEntity> {
    
    T map(FindCommodityDTO findCommodityDTO);
    
}
