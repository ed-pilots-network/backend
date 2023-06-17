package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.trade.domain.model.CommodityMarketInfo;
import io.edpn.backend.trade.domain.repository.CommodityMarketInfoRepository;
import io.edpn.backend.trade.domain.usecase.FindCommodityMarketInfoUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class DefaultFindCommodityMarketInfoUseCase implements FindCommodityMarketInfoUseCase {

    private final CommodityMarketInfoRepository commodityMarketInfoRepository;

    @Override
    public List<CommodityMarketInfo> findAll() {
        return commodityMarketInfoRepository.findAllCommodityMarketInfo();
    }
}
