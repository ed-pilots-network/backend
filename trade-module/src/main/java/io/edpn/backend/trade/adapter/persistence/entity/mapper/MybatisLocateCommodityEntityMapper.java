package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisLocateCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisValidatedCommodityEntity;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.PageInfo;
import io.edpn.backend.trade.application.dto.persistence.entity.LocateCommodityEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.LocateCommodityEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.ValidatedCommodityEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MybatisLocateCommodityEntityMapper implements LocateCommodityEntityMapper<MybatisLocateCommodityEntity> {

    private final ValidatedCommodityEntityMapper<MybatisValidatedCommodityEntity> validatedCommodityEntityMapper;
    private final StationEntityMapper<MybatisStationEntity> stationEntityMapper;

    @Override
    public LocateCommodity map(LocateCommodityEntity locateCommodityEntity) {
        return new LocateCommodity(
                locateCommodityEntity.getPriceUpdatedAt(),
                validatedCommodityEntityMapper.map(locateCommodityEntity.getValidatedCommodity()),
                stationEntityMapper.map(locateCommodityEntity.getStation()),
                locateCommodityEntity.getSupply(),
                locateCommodityEntity.getDemand(),
                locateCommodityEntity.getBuyPrice(),
                locateCommodityEntity.getSellPrice(),
                locateCommodityEntity.getDistance(),
                Optional.ofNullable(locateCommodityEntity.getPageSize()).map(pageSize ->
                        PageInfo.builder()
                                .pageSize(pageSize)
                                .currentPage(locateCommodityEntity.getCurrentPage())
                                .totalItems(locateCommodityEntity.getTotalItems())
                                .build()).orElse(null)
        );
    }

    @Override
    public MybatisLocateCommodityEntity map(LocateCommodity locateCommodity) {
        return MybatisLocateCommodityEntity.builder()
                .priceUpdatedAt(locateCommodity.getPriceUpdatedAt())
                .validatedCommodity(validatedCommodityEntityMapper.map(locateCommodity.getValidatedCommodity()))
                .station(stationEntityMapper.map(locateCommodity.getStation()))
                .supply(locateCommodity.getSupply())
                .demand(locateCommodity.getDemand())
                .buyPrice(locateCommodity.getBuyPrice())
                .sellPrice(locateCommodity.getSellPrice())
                .distance(locateCommodity.getDistance())
                .build();
    }
}
