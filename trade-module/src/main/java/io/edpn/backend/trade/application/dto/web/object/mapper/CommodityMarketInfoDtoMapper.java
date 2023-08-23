package io.edpn.backend.trade.application.dto.web.object.mapper;

import io.edpn.backend.trade.application.domain.CommodityMarketInfo;
import io.edpn.backend.trade.application.dto.web.object.CommodityMarketInfoDto;

public interface CommodityMarketInfoDtoMapper {
    
    CommodityMarketInfoDto map(CommodityMarketInfo commodityMarketInfo);
}
