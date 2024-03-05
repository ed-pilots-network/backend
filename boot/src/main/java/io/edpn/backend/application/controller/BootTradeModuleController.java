package io.edpn.backend.application.controller;

import io.edpn.backend.trade.adapter.web.TradeModuleController;
import io.edpn.backend.trade.adapter.web.dto.filter.mapper.RestFindCommodityFilterDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.filter.mapper.RestLocateCommodityFilterDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.RestCommodityMarketInfoDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.RestLocateCommodityDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.RestValidatedCommodityDtoMapper;
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
                                     GetFullCommodityMarketInfoUseCase getFullCommodityMarketInfoUseCase,
                                     RestValidatedCommodityDtoMapper restValidatedCommodityDtoMapper,
                                     RestLocateCommodityDtoMapper restLocateCommodityDtoMapper,
                                     RestCommodityMarketInfoDtoMapper restCommodityMarketInfoDtoMapper,
                                     RestFindCommodityFilterDtoMapper restFindCommodityFilterDtoMapper,
                                     RestLocateCommodityFilterDtoMapper restLocateCommodityFilterDtoMapper
    ) {
        super(
                findAllValidatedCommodityUseCase,
                findValidatedCommodityByNameUseCase,
                findValidatedCommodityByFilterUseCase,
                locateCommodityUseCase,
                getFullCommodityMarketInfoUseCase,
                restValidatedCommodityDtoMapper,
                restLocateCommodityDtoMapper,
                restCommodityMarketInfoDtoMapper,
                restFindCommodityFilterDtoMapper,
                restLocateCommodityFilterDtoMapper
        );
    }
}
