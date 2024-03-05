package io.edpn.backend.trade.adapter.web;

import io.edpn.backend.trade.adapter.web.dto.filter.FindCommodityFilterDto;
import io.edpn.backend.trade.adapter.web.dto.filter.LocateCommodityFilterDto;
import io.edpn.backend.trade.adapter.web.dto.filter.mapper.FindCommodityFilterDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.filter.mapper.LocateCommodityFilterDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.CommodityMarketInfoDto;
import io.edpn.backend.trade.adapter.web.dto.object.LocateCommodityDto;
import io.edpn.backend.trade.adapter.web.dto.object.ValidatedCommodityDto;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.CommodityMarketInfoDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.LocateCommodityDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.ValidatedCommodityDtoMapper;
import io.edpn.backend.trade.application.domain.filter.FindCommodityFilter;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
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

    private final ValidatedCommodityDtoMapper validatedCommodityDtoMapper;
    private final LocateCommodityDtoMapper locateCommodityDtoMapper;
    private final CommodityMarketInfoDtoMapper commodityMarketInfoDtoMapper;

    private final FindCommodityFilterDtoMapper findCommodityFilterDtoMapper;
    private final LocateCommodityFilterDtoMapper locateCommodityFilterDtoMapper;

    @GetMapping("/commodity")
    public List<ValidatedCommodityDto> findAll() {
        return findAllValidatedCommodityUseCase.findAll()
                .stream()
                .map(validatedCommodityDtoMapper::map)
                .toList();
    }

    @GetMapping("/commodity/filter")
    public List<ValidatedCommodityDto> findByFilter(FindCommodityFilterDto findCommodityRequest) {
        FindCommodityFilter findCommodityFilter = findCommodityFilterDtoMapper.map(findCommodityRequest);

        return findValidatedCommodityByFilterUseCase
                .findByFilter(findCommodityFilter)
                .stream()
                .map(validatedCommodityDtoMapper::map)
                .toList();
    }

    @GetMapping("/commodity/{displayName}")
    public Optional<ValidatedCommodityDto> findByName(@PathVariable String displayName) {
        return findValidatedCommodityByNameUseCase
                .findByName(displayName)
                .map(validatedCommodityDtoMapper::map);
    }

    @GetMapping("/locate-commodity/filter")
    public List<LocateCommodityDto> locateCommodityWithFilters(LocateCommodityFilterDto locateCommodityFilterDto) {
        LocateCommodityFilter locateCommodityFilter = locateCommodityFilterDtoMapper.map(locateCommodityFilterDto);

        return locateCommodityUseCase
                .locateCommodityOrderByDistance(locateCommodityFilter)
                .stream()
                .map(locateCommodityDtoMapper::map)
                .toList();
    }

    @GetMapping("/best-price")
    List<CommodityMarketInfoDto> fullMarketInfo() {
        return getFullCommodityMarketInfoUseCase
                .findAll()
                .stream()
                .map(commodityMarketInfoDtoMapper::map)
                .toList();
    }
}
