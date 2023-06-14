package io.edpn.backend.commodityfinder.application.service;

import io.edpn.backend.commodityfinder.application.dto.CommodityMarketInfoResponse;
import io.edpn.backend.commodityfinder.application.mappers.CommodityMarketInfoResponseMapper;
import io.edpn.backend.commodityfinder.domain.service.BestCommodityPriceService;
import io.edpn.backend.commodityfinder.domain.usecase.FindCommodityMarketInfoUseCase;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultBestCommodityPriceService implements BestCommodityPriceService {

    private final FindCommodityMarketInfoUseCase findCommodityMarketInfoUseCase;
    private final CommodityMarketInfoResponseMapper commodityMarketInfoResponseMapper;

    public List<CommodityMarketInfoResponse> getCommodityMarketInfo() {
        return findCommodityMarketInfoUseCase.findAll().stream()
                .map(commodityMarketInfoResponseMapper::map)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}