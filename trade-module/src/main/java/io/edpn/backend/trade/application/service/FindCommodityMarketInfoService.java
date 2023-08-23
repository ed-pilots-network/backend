package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.dto.web.object.CommodityMarketInfoDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.CommodityMarketInfoDtoMapper;
import io.edpn.backend.trade.application.port.incomming.commoditymarketinfo.GetFullCommodityMarketInfoUseCase;
import io.edpn.backend.trade.application.port.outgoing.commoditymarketinfo.GetFullCommodityMarketInfoPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindCommodityMarketInfoService implements GetFullCommodityMarketInfoUseCase {
    
    private final GetFullCommodityMarketInfoPort commodityMarketInfoPort;
    private final CommodityMarketInfoDtoMapper commodityMarketInfoDtoMapper;
    
    @Override
    public List<CommodityMarketInfoDto> findAll() {
        return commodityMarketInfoPort
                .findAll()
                .stream()
                .map(commodityMarketInfoDtoMapper::map)
                .toList();
    }
}
