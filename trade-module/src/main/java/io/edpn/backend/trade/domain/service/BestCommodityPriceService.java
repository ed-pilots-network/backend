package io.edpn.backend.trade.domain.service;

import io.edpn.backend.trade.application.dto.CommodityMarketInfoResponse;

import java.util.List;

public interface BestCommodityPriceService {

    List<CommodityMarketInfoResponse> getCommodityMarketInfo();
}
