package io.edpn.backend.trade.domain.controller.v1;

import io.edpn.backend.trade.application.dto.v1.CommodityMarketInfoResponse;
import io.edpn.backend.trade.application.dto.v1.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.FindCommodityResponse;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/trade")
public interface TradeModuleController {

    @GetMapping("/best-price")
    List<CommodityMarketInfoResponse> getBestCommodityPrice();

    @GetMapping("/locate-commodity/filter")
    List<LocateCommodityResponse> locateCommodityWithFilters(@Valid LocateCommodityRequest locateCommodityRequest);
    
    @GetMapping("/commodity/{displayName}")
    Optional<FindCommodityResponse> findValidatedCommodityByName(@PathVariable String displayName);
    
    @GetMapping("/commodity")
    List<FindCommodityResponse> findAllValidatedCommodities();
    
    @GetMapping("/commodity/filter")
    List<FindCommodityResponse> findValidatedCommodityByFilter(@Valid FindCommodityRequest findCommodityRequest);
}
