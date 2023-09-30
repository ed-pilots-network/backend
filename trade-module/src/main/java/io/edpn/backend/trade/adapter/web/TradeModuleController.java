package io.edpn.backend.trade.adapter.web;

import io.edpn.backend.trade.adapter.web.dto.filter.RestFindCommodityFilterDto;
import io.edpn.backend.trade.adapter.web.dto.filter.RestLocateCommodityFilterDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestCommodityMarketInfoDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestLocateCommodityDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestValidatedCommodityDto;
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
@RequestMapping("/api/v1/trade")
public class TradeModuleController {

    private final FindAllValidatedCommodityUseCase findAllValidatedCommodityUseCase;
    private final FindValidatedCommodityByNameUseCase findValidatedCommodityByNameUseCase;
    private final FindValidatedCommodityByFilterUseCase findValidatedCommodityByFilterUseCase;
    private final LocateCommodityUseCase locateCommodityUseCase;
    private final GetFullCommodityMarketInfoUseCase getFullCommodityMarketInfoUseCase;


    @GetMapping("/commodity")
    public List<RestValidatedCommodityDto> findAll() {
        return findAllValidatedCommodityUseCase.findAll()
                .stream()
                .map(x -> (RestValidatedCommodityDto)x)
                .toList();
    }

    @GetMapping("/commodity/filter")
    public List<RestValidatedCommodityDto> findByFilter(RestFindCommodityFilterDto findCommodityRequest) {
        return findValidatedCommodityByFilterUseCase
                .findByFilter(findCommodityRequest)
                .stream()
                .map(x -> (RestValidatedCommodityDto)x)
                .toList();
    }

    @GetMapping("/commodity/{displayName}")
    public Optional<RestValidatedCommodityDto> findByName(@PathVariable String displayName) {
        return findValidatedCommodityByNameUseCase
                .findByName(displayName)
                .map(x -> (RestValidatedCommodityDto)x);
    }
    
    @GetMapping("/locate-commodity/filter")
    public List<RestLocateCommodityDto> locateCommodityWithFilters(RestLocateCommodityFilterDto locateCommodityFilterDto){
        return locateCommodityUseCase
                .locateCommodityOrderByDistance(locateCommodityFilterDto)
                .stream()
                .map(x -> (RestLocateCommodityDto)x)
                .toList();
    }
    
    @GetMapping("/best-price")
    List<RestCommodityMarketInfoDto> fullMarketInfo() {
        return getFullCommodityMarketInfoUseCase
                .findAll()
                .stream()
                .map(x -> (RestCommodityMarketInfoDto)x)
                .toList();
    }
}
