package io.edpn.backend.trade.application.controller.v1;

import io.edpn.backend.trade.application.dto.v1.*;
import io.edpn.backend.trade.domain.controller.v1.TradeModuleController;
import io.edpn.backend.trade.domain.service.v1.FindCommodityService;
import io.edpn.backend.trade.domain.service.v1.LocateCommodityService;
import io.edpn.backend.trade.domain.service.v1.FindCommodityMarketInfoService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class DefaultTradeModuleController implements TradeModuleController {

    private final FindCommodityMarketInfoService findCommodityMarketInfoService;
    private final LocateCommodityService locateCommodityService;
    private final FindCommodityService findCommodityService;

    @Override
    public List<CommodityMarketInfoResponse> getBestCommodityPrice() {
        return findCommodityMarketInfoService.getCommodityMarketInfo();
    }

    @Override
    public List<LocateCommodityResponse> locateCommodityWithFilters(LocateCommodityRequest locateCommodityRequest) {
        return locateCommodityService.locateCommoditiesOrderByDistance(locateCommodityRequest);
    }
    
    @Override
    public Optional<FindCommodityResponse> findValidatedCommodityById(UUID id) {
        return findCommodityService.findById(id);
    }
    
    @Override
    public List<FindCommodityResponse> findAllValidatedCommodities() {
        return findCommodityService.findAll();
    }
    
    @Override
    public List<FindCommodityResponse> findValidatedCommodityByFilter(FindCommodityRequest findCommodityRequest) {
        return findCommodityService.findByFilter(findCommodityRequest);
    }
}
