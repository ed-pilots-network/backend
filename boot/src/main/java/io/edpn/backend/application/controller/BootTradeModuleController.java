package io.edpn.backend.application.controller;

import io.edpn.backend.trade.application.controller.v1.DefaultTradeModuleController;
import io.edpn.backend.trade.application.mappers.v1.CommodityMarketInfoResponseMapper;
import io.edpn.backend.trade.application.mappers.v1.FindCommodityDTOMapper;
import io.edpn.backend.trade.application.mappers.v1.LocateCommodityDTOMapper;
import io.edpn.backend.trade.domain.usecase.FindCommodityMarketInfoUseCase;
import io.edpn.backend.trade.domain.usecase.FindCommodityUseCase;
import io.edpn.backend.trade.domain.usecase.LocateCommodityUseCase;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BootTradeModuleController extends DefaultTradeModuleController {

    public BootTradeModuleController(
            FindCommodityMarketInfoUseCase findCommodityMarketInfoUseCase,
            LocateCommodityUseCase locateCommodityUseCase,
            FindCommodityUseCase findCommodityUseCase,

            CommodityMarketInfoResponseMapper commodityMarketInfoResponseMapper,
            LocateCommodityDTOMapper locateCommodityDTOMapper,
            FindCommodityDTOMapper findCommodityDTOMapper
    ) {
        super(findCommodityMarketInfoUseCase, locateCommodityUseCase, findCommodityUseCase, commodityMarketInfoResponseMapper, locateCommodityDTOMapper, findCommodityDTOMapper);
    }
}
