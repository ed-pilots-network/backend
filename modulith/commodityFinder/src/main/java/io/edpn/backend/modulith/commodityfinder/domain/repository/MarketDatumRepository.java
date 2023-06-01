package io.edpn.backend.modulith.commodityfinder.domain.repository;

import io.edpn.backend.modulith.commodityfinder.application.dto.persistence.BestCommodityPriceEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MarketDatumRepository {
    Optional<BestCommodityPriceEntity> findBestCommodityPriceEntityByCommodityId(UUID commodityId);

    List<BestCommodityPriceEntity> findAllBestCommodityPrices();
}
