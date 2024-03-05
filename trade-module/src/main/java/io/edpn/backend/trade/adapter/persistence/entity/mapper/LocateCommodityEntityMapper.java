package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.LocateCommodityEntity;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocateCommodityEntityMapper {

    private final ValidatedCommodityEntityMapper validatedCommodityEntityMapper;
    private final StationEntityMapper stationEntityMapper;

    public LocateCommodity map(LocateCommodityEntity locateCommodityEntity) {
        return new LocateCommodity(
                locateCommodityEntity.getPriceUpdatedAt(),
                validatedCommodityEntityMapper.map(locateCommodityEntity.getValidatedCommodity()),
                stationEntityMapper.map(locateCommodityEntity.getStation()),
                locateCommodityEntity.getSupply(),
                locateCommodityEntity.getDemand(),
                locateCommodityEntity.getBuyPrice(),
                locateCommodityEntity.getSellPrice(),
                locateCommodityEntity.getDistance()
        );
    }

    public LocateCommodityEntity map(LocateCommodity locateCommodity) {
        return LocateCommodityEntity.builder()
                .priceUpdatedAt(locateCommodity.priceUpdatedAt())
                .validatedCommodity(validatedCommodityEntityMapper.map(locateCommodity.validatedCommodity()))
                .station(stationEntityMapper.map(locateCommodity.station()))
                .supply(locateCommodity.supply())
                .demand(locateCommodity.demand())
                .buyPrice(locateCommodity.buyPrice())
                .sellPrice(locateCommodity.sellPrice())
                .distance(locateCommodity.distance())
                .build();
    }
}
