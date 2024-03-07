package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisCommodityMarketInfoEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisCommodityMarketInfoRepository;
import io.edpn.backend.trade.application.domain.CommodityMarketInfo;
import io.edpn.backend.trade.application.port.outgoing.commoditymarketinfo.GetFullCommodityMarketInfoPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CommodityMarketInfoRepository implements GetFullCommodityMarketInfoPort {

    private final MybatisCommodityMarketInfoRepository mybatisCommodityMarketInfoRepository;
    private final MybatisCommodityMarketInfoEntityMapper mybatisCommodityMarketInfoEntityMapper;

    @Override
    public List<CommodityMarketInfo> findAll() {
        return mybatisCommodityMarketInfoRepository
                .findAll()
                .stream()
                .map(mybatisCommodityMarketInfoEntityMapper::map)
                .toList();
    }
}
