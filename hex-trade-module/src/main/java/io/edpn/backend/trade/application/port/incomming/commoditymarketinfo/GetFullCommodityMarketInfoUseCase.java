package io.edpn.backend.trade.application.port.incomming.commoditymarketinfo;

import io.edpn.backend.trade.application.dto.web.object.CommodityMarketInfoDto;

import java.util.List;

public interface GetFullCommodityMarketInfoUseCase {
    
    List<CommodityMarketInfoDto> findAll();
}
