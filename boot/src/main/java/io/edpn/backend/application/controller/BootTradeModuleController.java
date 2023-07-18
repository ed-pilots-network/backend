package io.edpn.backend.application.controller;

import io.edpn.backend.trade.application.controller.v1.DefaultTradeModuleController;
import io.edpn.backend.trade.domain.service.v1.FindCommodityMarketInfoService;
import io.edpn.backend.trade.domain.service.v1.LocateCommodityService;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BootTradeModuleController extends DefaultTradeModuleController {

    public BootTradeModuleController(FindCommodityMarketInfoService findCommodityMarketInfoService, LocateCommodityService locateCommodityService) {
        super(findCommodityMarketInfoService, locateCommodityService);
    }
}
