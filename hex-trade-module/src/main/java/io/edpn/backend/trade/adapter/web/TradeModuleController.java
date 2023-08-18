package io.edpn.backend.trade.adapter.web;

import io.edpn.backend.trade.application.dto.web.filter.FindCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.filter.LocateCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.object.CommodityMarketInfoDto;
import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.ValidatedCommodityDto;
import io.edpn.backend.trade.application.port.incomming.commoditymarketinfo.GetFullCommodityMarketInfoUseCase;
import io.edpn.backend.trade.application.port.incomming.locatecommodity.LocateCommodityUseCase;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindAllValidatedCommodityUseCase;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByFilterUseCase;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByNameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/v1/api/trade")
public class TradeModuleController {

    private final FindAllValidatedCommodityUseCase findAllValidatedCommodityUseCase;
    private final FindValidatedCommodityByNameUseCase findValidatedCommodityByNameUseCase;
    private final FindValidatedCommodityByFilterUseCase findValidatedCommodityByFilterUseCase;
    private final LocateCommodityUseCase locateCommodityUseCase;
    private final GetFullCommodityMarketInfoUseCase getFullCommodityMarketInfoUseCase;


    @GetMapping("/commodity")
    public List<ValidatedCommodityDto> findAll() {
        return findAllValidatedCommodityUseCase.findAll();
    }

    @GetMapping("/commodity/filter")
    public List<ValidatedCommodityDto> findByFilter(FindCommodityFilterDto findCommodityRequest) {
        return findValidatedCommodityByFilterUseCase.findByFilter(findCommodityRequest);
    }

    @GetMapping("/commodity/{displayName}")
    public Optional<ValidatedCommodityDto> findByName(@PathVariable String displayName) {
        return findValidatedCommodityByNameUseCase.findByName(displayName);
    }
    
    @GetMapping("/locate-commodity/filter")
    public List<LocateCommodityDto> locateCommodityWithFilters(LocateCommodityFilterDto locateCommodityFilterDto){
        return locateCommodityUseCase.locateCommodityOrderByDistance(locateCommodityFilterDto);
    }
    
    @GetMapping("/best-price")
    List<CommodityMarketInfoDto> fullMarketInfo() {
        return getFullCommodityMarketInfoUseCase.findAll();
    }
}
