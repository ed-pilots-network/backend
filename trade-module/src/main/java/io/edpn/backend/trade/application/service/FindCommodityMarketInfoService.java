package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.domain.CommodityMarketInfo;
import io.edpn.backend.trade.application.port.incomming.commoditymarketinfo.GetFullCommodityMarketInfoUseCase;
import io.edpn.backend.trade.application.port.outgoing.commoditymarketinfo.GetFullCommodityMarketInfoPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindCommodityMarketInfoService implements GetFullCommodityMarketInfoUseCase {

    private final GetFullCommodityMarketInfoPort commodityMarketInfoPort;

    @Override
    public List<CommodityMarketInfo> findAll() {
        return commodityMarketInfoPort.findAll();
    }
}
