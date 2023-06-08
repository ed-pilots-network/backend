package io.edpn.backend.commodityfinder.domain.usecase;

import io.edpn.backend.commodityfinder.domain.model.CommodityMarketInfo;

import java.util.List;

public interface FindCommodityMarketInfoUseCase {

    List<CommodityMarketInfo> findAll();
}
