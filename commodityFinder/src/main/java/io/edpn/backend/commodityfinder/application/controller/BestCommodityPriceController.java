package io.edpn.backend.commodityfinder.application.controller;

import io.edpn.backend.commodityfinder.application.dto.rest.BestCommodityPriceResponse;
import io.edpn.backend.commodityfinder.application.mappers.rest.BestCommodityPriceResponseMapper;
import io.edpn.backend.commodityfinder.domain.usecase.FindBestCommodityPriceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/commodity")
@RequiredArgsConstructor
public class BestCommodityPriceController {

    private final FindBestCommodityPriceUseCase findBestCommodityPriceUseCase;
    private final BestCommodityPriceResponseMapper bestCommodityPriceResponseMapper;

    @GetMapping("/best-price")
    public List<BestCommodityPriceResponse> getBestCommodityPrice() {
        return findBestCommodityPriceUseCase.findAll().stream().map(bestCommodityPriceResponseMapper::map).toList();
    }
}
