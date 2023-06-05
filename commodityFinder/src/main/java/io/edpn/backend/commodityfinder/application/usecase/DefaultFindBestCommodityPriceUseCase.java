package io.edpn.backend.commodityfinder.application.usecase;

import io.edpn.backend.commodityfinder.domain.model.BestCommodityPrice;
import io.edpn.backend.commodityfinder.domain.repository.MarketDatumRepository;
import io.edpn.backend.commodityfinder.domain.usecase.FindBestCommodityPriceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class DefaultFindBestCommodityPriceUseCase implements FindBestCommodityPriceUseCase {

    private final MarketDatumRepository marketDatumRepository;

    @Override
    public List<BestCommodityPrice> findAll() {
        return marketDatumRepository.findAllBestCommodityPrices();
    }
}
