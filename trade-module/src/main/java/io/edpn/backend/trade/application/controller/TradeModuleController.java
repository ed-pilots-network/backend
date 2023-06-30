package io.edpn.backend.trade.application.controller;

import io.edpn.backend.trade.application.dto.CommodityMarketInfoResponse;
import io.edpn.backend.trade.application.dto.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.FindCommodityResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("trade")
public interface TradeModuleController {

    @GetMapping("/best-price")
    List<CommodityMarketInfoResponse> getBestCommodityPrice();
    
    @PostMapping("/commodity")
    List<FindCommodityResponse> findByCommodityWithFilters(@Valid @RequestBody FindCommodityRequest locateCommodityRequest);
}
