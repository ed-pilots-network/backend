package io.edpn.backend.commodityfinder.domain.usecase;

import io.edpn.backend.commodityfinder.domain.model.BestCommodityPrice;

import java.util.List;

public interface FindBestCommodityPriceUseCase {

    List<BestCommodityPrice> findAll();
}
