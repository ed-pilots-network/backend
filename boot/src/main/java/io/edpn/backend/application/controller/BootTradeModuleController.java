package io.edpn.backend.application.controller;

import io.edpn.backend.trade.application.controller.v1.DefaultTradeModuleController;
import io.edpn.backend.trade.domain.service.v1.BestCommodityPriceService;
import io.edpn.backend.trade.domain.service.v1.LocateCommodityService;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BootTradeModuleController extends DefaultTradeModuleController {

    public BootTradeModuleController(BestCommodityPriceService bestCommodityPriceService, LocateCommodityService locateCommodityService) {
        super(bestCommodityPriceService, locateCommodityService);
    }
}
