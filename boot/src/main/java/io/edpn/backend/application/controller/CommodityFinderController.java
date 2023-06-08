package io.edpn.backend.application.controller;

import io.edpn.backend.commodityfinder.application.controller.BestCommodityPriceController;
import io.edpn.backend.commodityfinder.application.dto.CommodityMarketInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("commodityfinder")
@RequiredArgsConstructor
public class CommodityFinderController {

    private final BestCommodityPriceController bestCommodityPriceController;

    @GetMapping("/best-price")
    public List<CommodityMarketInfoResponse> getBestCommodityPrice() {
        return bestCommodityPriceController.getCommodityMarketInfo();
    }
}
