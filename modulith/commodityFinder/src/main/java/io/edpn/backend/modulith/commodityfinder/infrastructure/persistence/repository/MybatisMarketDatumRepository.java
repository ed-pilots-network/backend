package io.edpn.backend.modulith.commodityfinder.infrastructure.persistence.repository;

import io.edpn.backend.modulith.commodityfinder.application.dto.persistence.BestCommodityPriceEntity;
import io.edpn.backend.modulith.commodityfinder.application.dto.persistence.CommodityEntity;
import io.edpn.backend.modulith.commodityfinder.domain.repository.MarketDatumRepository;
import io.edpn.backend.modulith.commodityfinder.infrastructure.persistence.mappers.CommodityEntityMapper;
import io.edpn.backend.modulith.commodityfinder.infrastructure.persistence.mappers.MarketDatumEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MybatisMarketDatumRepository implements MarketDatumRepository {

    private final MarketDatumEntityMapper marketDatumEntityMapper;
    private final CommodityEntityMapper commodityEntityMapper;

    @Override
    public Optional<BestCommodityPriceEntity> findBestCommodityPriceEntityByCommodityId(UUID commodityId) {
        return marketDatumEntityMapper.getBestCommodityPrice(commodityId);
    }

    @Override
    public List<BestCommodityPriceEntity> findAllBestCommodityPrices() {
        return commodityEntityMapper.findAll().stream()
                .map(CommodityEntity::getId)
                .map(marketDatumEntityMapper::getBestCommodityPrice)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

}
