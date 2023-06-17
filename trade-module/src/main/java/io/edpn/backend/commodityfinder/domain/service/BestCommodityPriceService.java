package io.edpn.backend.commodityfinder.domain.service;

import io.edpn.backend.commodityfinder.application.dto.CommodityMarketInfoResponse;

import java.util.List;

public interface BestCommodityPriceService {

    List<CommodityMarketInfoResponse> getCommodityMarketInfo();
}
