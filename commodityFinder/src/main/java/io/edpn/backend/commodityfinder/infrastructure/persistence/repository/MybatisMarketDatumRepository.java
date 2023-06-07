package io.edpn.backend.commodityfinder.infrastructure.persistence.repository;

import io.edpn.backend.commodityfinder.domain.model.BestCommodityPrice;
import io.edpn.backend.commodityfinder.domain.repository.MarketDatumRepository;
import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.CommodityEntity;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.entity.BestCommodityPriceMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.CommodityEntityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.MarketDatumEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MybatisMarketDatumRepository implements MarketDatumRepository {

    private final MarketDatumEntityMapper marketDatumEntityMapper;
    private final BestCommodityPriceMapper bestCommodityPriceMapper;
    private final CommodityEntityMapper commodityEntityMapper;

    @Override
    public Optional<BestCommodityPrice> findBestCommodityPriceEntityByCommodityId(UUID commodityId) {
        return marketDatumEntityMapper.getBestCommodityPrice(commodityId)
                .map(bestCommodityPriceMapper::map);
    }

    @Override
    public List<BestCommodityPrice> findAllBestCommodityPrices() {
        return commodityEntityMapper.findAll().stream()
                .map(CommodityEntity::getId)
                .map(marketDatumEntityMapper::getBestCommodityPrice)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(bestCommodityPriceMapper::map)
                .collect(Collectors.toCollection(LinkedList::new));
    }

}
