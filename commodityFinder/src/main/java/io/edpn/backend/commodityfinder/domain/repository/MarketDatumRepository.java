package io.edpn.backend.commodityfinder.domain.repository;

import io.edpn.backend.commodityfinder.domain.model.BestCommodityPrice;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MarketDatumRepository {
    Optional<BestCommodityPrice> findBestCommodityPriceEntityByCommodityId(UUID commodityId);

    List<BestCommodityPrice> findAllBestCommodityPrices();
}
