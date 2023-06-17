package io.edpn.backend.trade.application.controller;

import io.edpn.backend.trade.application.dto.CommodityMarketInfoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("trade")
public interface TradeModuleController {

    @GetMapping("/best-price")
    List<CommodityMarketInfoResponse> getBestCommodityPrice();
}
