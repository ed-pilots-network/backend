package io.edpn.backend.trade.domain.service.v1;

import io.edpn.backend.trade.application.dto.v1.CommodityMarketInfoResponse;

import java.util.List;

public interface BestCommodityPriceService {

    List<CommodityMarketInfoResponse> getCommodityMarketInfo();
}
