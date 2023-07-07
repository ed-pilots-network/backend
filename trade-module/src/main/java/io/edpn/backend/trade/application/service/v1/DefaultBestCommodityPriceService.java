package io.edpn.backend.trade.application.service.v1;

import io.edpn.backend.trade.application.dto.v1.CommodityMarketInfoResponse;
import io.edpn.backend.trade.application.mappers.v1.CommodityMarketInfoResponseMapper;
import io.edpn.backend.trade.domain.service.v1.BestCommodityPriceService;
import io.edpn.backend.trade.domain.usecase.FindCommodityMarketInfoUseCase;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultBestCommodityPriceService implements BestCommodityPriceService {

    private final FindCommodityMarketInfoUseCase findCommodityMarketInfoUseCase;
    private final CommodityMarketInfoResponseMapper commodityMarketInfoResponseMapper;

    public List<CommodityMarketInfoResponse> getCommodityMarketInfo() {
        return findCommodityMarketInfoUseCase.findAll().stream()
                .map(commodityMarketInfoResponseMapper::map)
                .toList();
    }
}
