package io.edpn.backend.commodityfinder.domain.usecase;

import io.edpn.backend.commodityfinder.domain.entity.BestCommodityPrice;

import java.util.List;

public interface FindBestCommodityPriceUseCase {

    List<BestCommodityPrice> findAll();
}
