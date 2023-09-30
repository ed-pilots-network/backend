package io.edpn.backend.application.controller;

import io.edpn.backend.trade.adapter.web.TradeModuleController;
import io.edpn.backend.trade.application.port.incomming.commoditymarketinfo.GetFullCommodityMarketInfoUseCase;
import io.edpn.backend.trade.application.port.incomming.locatecommodity.LocateCommodityUseCase;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindAllValidatedCommodityUseCase;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByFilterUseCase;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByNameUseCase;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BootTradeModuleController extends TradeModuleController {
    public BootTradeModuleController(FindAllValidatedCommodityUseCase findAllValidatedCommodityUseCase,
                                     FindValidatedCommodityByNameUseCase findValidatedCommodityByNameUseCase,
                                     FindValidatedCommodityByFilterUseCase findValidatedCommodityByFilterUseCase,
                                     LocateCommodityUseCase locateCommodityUseCase,
                                     GetFullCommodityMarketInfoUseCase getFullCommodityMarketInfoUseCase
                                     ) {
        super(findAllValidatedCommodityUseCase, findValidatedCommodityByNameUseCase, findValidatedCommodityByFilterUseCase, locateCommodityUseCase, getFullCommodityMarketInfoUseCase);
    }
}
