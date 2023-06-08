package io.edpn.backend.commodityfinder.domain.repository;

import io.edpn.backend.commodityfinder.domain.model.CommodityMarketInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommodityMarketInfoRepository {
    Optional<CommodityMarketInfo> findCommodityMarketInfoByCommodityId(UUID commodityId);

    List<CommodityMarketInfo> findAllCommodityMarketInfo();
}
