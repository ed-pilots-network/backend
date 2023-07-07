package io.edpn.backend.trade.application.controller.v1;

import io.edpn.backend.trade.application.dto.v1.CommodityMarketInfoResponse;
import io.edpn.backend.trade.application.dto.v1.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.FindCommodityResponse;
import io.edpn.backend.trade.domain.controller.v1.TradeModuleController;
import io.edpn.backend.trade.domain.service.v1.FindCommodityService;
import io.edpn.backend.trade.domain.service.v1.BestCommodityPriceService;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class DefaultTradeModuleController implements TradeModuleController {

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
