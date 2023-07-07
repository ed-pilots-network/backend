package io.edpn.backend.trade.domain.controller.v1;

import io.edpn.backend.trade.application.dto.v1.CommodityMarketInfoResponse;
import io.edpn.backend.trade.application.dto.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.FindCommodityResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/trade")
public interface TradeModuleController {

    @GetMapping("/best-price")
    List<CommodityMarketInfoResponse> getBestCommodityPrice();

    @PostMapping("/commodity")
    List<FindCommodityResponse> findByCommodityWithFilters(@Valid @RequestBody FindCommodityRequest locateCommodityRequest);
}
