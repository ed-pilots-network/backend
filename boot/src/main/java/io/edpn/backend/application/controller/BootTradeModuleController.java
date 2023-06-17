package io.edpn.backend.application.controller;

import io.edpn.backend.trade.domain.service.BestCommodityPriceService;
import io.edpn.backend.trade.application.controller.TradeModuleController;
import io.edpn.backend.trade.application.dto.CommodityMarketInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BootTradeModuleController implements TradeModuleController {

    private final BestCommodityPriceService bestCommodityPriceService;

    @Override
    public List<CommodityMarketInfoResponse> getBestCommodityPrice() {
        return bestCommodityPriceService.getCommodityMarketInfo();
    }
}
