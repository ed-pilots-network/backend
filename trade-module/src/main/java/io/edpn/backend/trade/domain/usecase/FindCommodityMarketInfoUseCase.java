package io.edpn.backend.trade.domain.usecase;

import io.edpn.backend.trade.domain.model.CommodityMarketInfo;

import java.util.List;

public interface FindCommodityMarketInfoUseCase {

    List<CommodityMarketInfo> findAll();
}
