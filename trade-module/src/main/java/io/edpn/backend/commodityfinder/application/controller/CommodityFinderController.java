package io.edpn.backend.commodityfinder.application.controller;

import io.edpn.backend.commodityfinder.application.dto.CommodityMarketInfoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("trade")
public interface CommodityFinderController {

    @GetMapping("/best-price")
    List<CommodityMarketInfoResponse> getBestCommodityPrice();
}
