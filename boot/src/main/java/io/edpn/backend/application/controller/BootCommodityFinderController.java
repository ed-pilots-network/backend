package io.edpn.backend.application.controller;

import io.edpn.backend.commodityfinder.domain.service.BestCommodityPriceService;
import io.edpn.backend.commodityfinder.application.controller.CommodityFinderController;
import io.edpn.backend.commodityfinder.application.dto.CommodityMarketInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BootCommodityFinderController implements CommodityFinderController {

    private final BestCommodityPriceService bestCommodityPriceService;

    @Override
    public List<CommodityMarketInfoResponse> getBestCommodityPrice() {
        return bestCommodityPriceService.getCommodityMarketInfo();
    }
}
