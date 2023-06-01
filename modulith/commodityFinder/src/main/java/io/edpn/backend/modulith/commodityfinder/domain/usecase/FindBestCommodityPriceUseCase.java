package io.edpn.backend.modulith.commodityfinder.domain.usecase;

import io.edpn.backend.modulith.commodityfinder.domain.entity.BestCommodityPrice;

import java.util.List;

public interface FindBestCommodityPriceUseCase {

    List<BestCommodityPrice> findAll();
}
