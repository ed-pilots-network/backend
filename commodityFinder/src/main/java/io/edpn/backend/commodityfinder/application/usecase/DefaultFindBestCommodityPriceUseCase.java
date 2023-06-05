package io.edpn.backend.commodityfinder.application.usecase;

import io.edpn.backend.commodityfinder.application.service.MarketDatumService;
import io.edpn.backend.commodityfinder.domain.entity.BestCommodityPrice;
import io.edpn.backend.commodityfinder.domain.usecase.FindBestCommodityPriceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class DefaultFindBestCommodityPriceUseCase implements FindBestCommodityPriceUseCase {

    private final MarketDatumService marketDatumService;

    @Override
    public List<BestCommodityPrice> findAll() {
        return marketDatumService.findAll();
    }
}
