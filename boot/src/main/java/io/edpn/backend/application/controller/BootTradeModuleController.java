package io.edpn.backend.application.controller;

import io.edpn.backend.trade.application.dto.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.FindCommodityResponse;
import io.edpn.backend.trade.domain.service.BestCommodityPriceService;
import io.edpn.backend.trade.application.controller.TradeModuleController;
import io.edpn.backend.trade.application.dto.CommodityMarketInfoResponse;
import io.edpn.backend.trade.domain.service.FindCommodityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BootTradeModuleController extends DefaultTradeModuleController {

    public BootTradeModuleController(BestCommodityPriceService bestCommodityPriceService) {
        super(bestCommodityPriceService);
    }
}
