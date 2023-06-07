package io.edpn.backend.commodityfinder.application.controller;

import io.edpn.backend.commodityfinder.application.dto.BestCommodityPriceResponse;
import io.edpn.backend.commodityfinder.application.mappers.BestCommodityPriceResponseMapper;
import io.edpn.backend.commodityfinder.domain.usecase.FindBestCommodityPriceUseCase;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BestCommodityPriceController {

    private final FindBestCommodityPriceUseCase findBestCommodityPriceUseCase;
    private final BestCommodityPriceResponseMapper bestCommodityPriceResponseMapper;

    public List<BestCommodityPriceResponse> getBestCommodityPrice() {
        return findBestCommodityPriceUseCase.findAll().stream()
                .map(bestCommodityPriceResponseMapper::map)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
