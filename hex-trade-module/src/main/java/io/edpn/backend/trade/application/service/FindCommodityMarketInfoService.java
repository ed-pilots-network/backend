package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.domain.CommodityMarketInfo;
import io.edpn.backend.trade.application.port.incomming.commoditymarketinfo.GetFullCommodityMarketInfoUseCase;

import java.util.List;

public class FindCommodityMarketInfoService implements GetFullCommodityMarketInfoUseCase {
    @Override
    public List<CommodityMarketInfo> findAll() {
        return null;
    }
}
