package io.edpn.backend.application.controller;

import io.edpn.backend.trade.application.controller.DefaultTradeModuleController;
import io.edpn.backend.trade.domain.service.BestCommodityPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BootTradeModuleController extends DefaultTradeModuleController {

    @Autowired
    public BootTradeModuleController(BestCommodityPriceService bestCommodityPriceService) {
        super(bestCommodityPriceService);
    }
}
