package io.edpn.backend.trade.domain.controller.v1;

import io.edpn.backend.trade.application.dto.v1.*;
import io.edpn.backend.trade.domain.model.ValidatedCommodity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/api/v1/trade")
public interface TradeModuleController {

    @GetMapping("/best-price")
    List<CommodityMarketInfoResponse> getBestCommodityPrice();

    @PostMapping("/locate-commodity")
    List<LocateCommodityResponse> locateCommodityWithFilters(@Valid @RequestBody LocateCommodityRequest locateCommodityRequest);
    
    @GetMapping("/commodity/{id}")
    Optional<FindCommodityResponse> findValidatedCommodityById(@PathVariable UUID id);
    
    @GetMapping("/commodity")
    List<FindCommodityResponse> findAllValidatedCommodities();
    
    @PostMapping("/commodity/filter")
    List<FindCommodityResponse> findValidatedCommodityByFilter(@Valid @RequestBody FindCommodityRequest findCommodityRequest);
}
