package io.edpn.backend.trade.application.controller;

import io.edpn.backend.trade.application.dto.CommodityMarketInfoResponse;
import io.edpn.backend.trade.domain.controller.TradeModuleController;
import io.edpn.backend.trade.domain.service.BestCommodityPriceService;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class DefaultTradeModuleController implements TradeModuleController {

    private final BestCommodityPriceService bestCommodityPriceService;

    @Override
    public List<CommodityMarketInfoResponse> getBestCommodityPrice() {
        return bestCommodityPriceService.getCommodityMarketInfo();
    }
}
