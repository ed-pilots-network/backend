package io.edpn.backend.trade.application.controller.v1;

import io.edpn.backend.trade.application.dto.v1.CommodityMarketInfoResponse;
import io.edpn.backend.trade.application.dto.v1.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.FindCommodityResponse;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityResponse;
import io.edpn.backend.trade.application.mappers.v1.CommodityMarketInfoResponseMapper;
import io.edpn.backend.trade.application.mappers.v1.FindCommodityDTOMapper;
import io.edpn.backend.trade.application.mappers.v1.LocateCommodityDTOMapper;
import io.edpn.backend.trade.domain.controller.v1.TradeModuleController;
import io.edpn.backend.trade.domain.usecase.FindCommodityMarketInfoUseCase;
import io.edpn.backend.trade.domain.usecase.FindCommodityUseCase;
import io.edpn.backend.trade.domain.usecase.LocateCommodityUseCase;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class DefaultTradeModuleController implements TradeModuleController {

    private final FindCommodityMarketInfoUseCase findCommodityMarketInfoUseCase;
    private final LocateCommodityUseCase locateCommodityUseCase;
    private final FindCommodityUseCase findCommodityUseCase;

    private final CommodityMarketInfoResponseMapper commodityMarketInfoResponseMapper;
    private final LocateCommodityDTOMapper locateCommodityDTOMapper;
    private final FindCommodityDTOMapper findCommodityDTOMapper;


    @Override
    public List<CommodityMarketInfoResponse> getBestCommodityPrice() {
        return findCommodityMarketInfoUseCase.findAll()
                .stream()
                .map(commodityMarketInfoResponseMapper::map)
                .toList();
    }

    @Override
    public List<LocateCommodityResponse> locateCommodityWithFilters(LocateCommodityRequest locateCommodityRequest) {
        return Optional.ofNullable(locateCommodityRequest)
                .map(locateCommodityDTOMapper::map)
                .map(locateCommodityUseCase::locateCommoditiesOrderByDistance)
                .map(list -> list.stream().map(locateCommodityDTOMapper::map).toList())
                .orElse(Collections.emptyList());
    }

    @Override
    public Optional<FindCommodityResponse> findValidatedCommodityByName(String displayName) {
        return findCommodityUseCase.findByName(displayName)
                .map(findCommodityDTOMapper::map);
    }

    @Override
    public List<FindCommodityResponse> findAllValidatedCommodities() {
        return findCommodityUseCase.findAll()
                .stream()
                .map(findCommodityDTOMapper::map)
                .toList();
    }

    @Override
    public List<FindCommodityResponse> findValidatedCommodityByFilter(FindCommodityRequest findCommodityRequest) {
        return Optional.ofNullable(findCommodityRequest)
                .map(findCommodityDTOMapper::map)
                .map(findCommodityUseCase::findByFilter)
                .map(list -> list.stream().map(findCommodityDTOMapper::map).toList())
                .orElse(Collections.emptyList());
    }
}
