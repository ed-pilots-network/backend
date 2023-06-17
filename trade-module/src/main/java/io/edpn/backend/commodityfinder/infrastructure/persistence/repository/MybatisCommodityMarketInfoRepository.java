package io.edpn.backend.commodityfinder.infrastructure.persistence.repository;

import io.edpn.backend.commodityfinder.domain.model.CommodityMarketInfo;
import io.edpn.backend.commodityfinder.domain.repository.CommodityMarketInfoRepository;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.entity.CommodityMarketInfoMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.CommodityMarketInfoEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MybatisCommodityMarketInfoRepository implements CommodityMarketInfoRepository {

    private final CommodityMarketInfoMapper commodityMarketInfoMapper;
    private final CommodityMarketInfoEntityMapper commodityMarketInfoEntityMapper;

    @Override
    public Optional<CommodityMarketInfo> findCommodityMarketInfoByCommodityId(UUID commodityId) {
        return commodityMarketInfoEntityMapper.findByCommodityId(commodityId)
                .map(commodityMarketInfoMapper::map);
    }

    @Override
    public List<CommodityMarketInfo> findAllCommodityMarketInfo() {
        return commodityMarketInfoEntityMapper.findAll()
                .stream().map(commodityMarketInfoMapper::map)
                .collect(Collectors.toCollection(LinkedList::new));
    }

}
