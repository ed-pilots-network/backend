package io.edpn.backend.trade.application.port.incomming.commoditymarketinfo;

import io.edpn.backend.trade.application.domain.CommodityMarketInfo;

import java.util.List;

public interface GetFullCommodityMarketInfoUseCase {

    List<CommodityMarketInfo> findAll();
}
