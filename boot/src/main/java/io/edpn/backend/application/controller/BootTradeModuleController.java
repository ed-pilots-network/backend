package io.edpn.backend.application.controller;

import io.edpn.backend.trade.application.dto.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.FindCommodityResponse;
import io.edpn.backend.trade.domain.service.BestCommodityPriceService;
import io.edpn.backend.trade.application.controller.TradeModuleController;
import io.edpn.backend.trade.application.dto.CommodityMarketInfoResponse;
import io.edpn.backend.trade.domain.service.FindCommodityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BootTradeModuleController implements TradeModuleController {

    private final BestCommodityPriceService bestCommodityPriceService;
    private final FindCommodityService findCommodityService;

    @Override
    public List<CommodityMarketInfoResponse> getBestCommodityPrice() {
        return bestCommodityPriceService.getCommodityMarketInfo();
    }
    
    @Override
    public List<FindCommodityResponse> findByCommodityWithFilters(FindCommodityRequest locateCommodityRequest) {
        return findCommodityService.findCommoditiesNearby(locateCommodityRequest);
    }
}
