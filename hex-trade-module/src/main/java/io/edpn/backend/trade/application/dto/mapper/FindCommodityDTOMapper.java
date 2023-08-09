package io.edpn.backend.trade.application.dto.mapper;

import io.edpn.backend.trade.application.dto.FindCommodityDTO;
import io.edpn.backend.trade.application.dto.FindCommodityEntity;

public interface FindCommodityDTOMapper {
    
    FindCommodityDTO map(FindCommodityEntity findCommodityEntity);
}
