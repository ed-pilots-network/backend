package io.edpn.backend.trade.application.port.outgoing.commoditymarketinfo;

import io.edpn.backend.trade.application.domain.CommodityMarketInfo;

import java.util.List;

public interface GetFullCommodityMarketInfoPort {
    
    List<CommodityMarketInfo> findAll();
}
