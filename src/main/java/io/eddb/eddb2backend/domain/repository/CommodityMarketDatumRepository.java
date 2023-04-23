package io.eddb.eddb2backend.domain.repository;

import io.eddb.eddb2backend.application.dto.persistence.CommodityMarketDatumEntity;

import java.util.Optional;

public interface CommodityMarketDatumRepository {
    CommodityMarketDatumEntity update(CommodityMarketDatumEntity entity);

    Optional<CommodityMarketDatumEntity> getById(CommodityMarketDatumEntity entity);

    CommodityMarketDatumEntity create(CommodityMarketDatumEntity entity);

}
