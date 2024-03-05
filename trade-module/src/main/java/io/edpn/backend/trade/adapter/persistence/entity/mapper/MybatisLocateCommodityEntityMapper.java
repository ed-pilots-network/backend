package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisLocateCommodityEntity;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisLocateCommodityEntityMapper {

    private final MybatisValidatedCommodityEntityMapper mybatisValidatedCommodityEntityMapper;
    private final MybatisStationEntityMapper mybatisStationEntityMapper;

    public LocateCommodity map(MybatisLocateCommodityEntity mybatisLocateCommodityEntity) {
        return new LocateCommodity(
                mybatisLocateCommodityEntity.getPriceUpdatedAt(),
                mybatisValidatedCommodityEntityMapper.map(mybatisLocateCommodityEntity.getValidatedCommodity()),
                mybatisStationEntityMapper.map(mybatisLocateCommodityEntity.getStation()),
                mybatisLocateCommodityEntity.getSupply(),
                mybatisLocateCommodityEntity.getDemand(),
                mybatisLocateCommodityEntity.getBuyPrice(),
                mybatisLocateCommodityEntity.getSellPrice(),
                mybatisLocateCommodityEntity.getDistance()
        );
    }

    public MybatisLocateCommodityEntity map(LocateCommodity locateCommodity) {
        return MybatisLocateCommodityEntity.builder()
                .priceUpdatedAt(locateCommodity.priceUpdatedAt())
                .validatedCommodity(mybatisValidatedCommodityEntityMapper.map(locateCommodity.validatedCommodity()))
                .station(mybatisStationEntityMapper.map(locateCommodity.station()))
                .supply(locateCommodity.supply())
                .demand(locateCommodity.demand())
                .buyPrice(locateCommodity.buyPrice())
                .sellPrice(locateCommodity.sellPrice())
                .distance(locateCommodity.distance())
                .build();
    }
}
