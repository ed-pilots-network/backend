package io.edpn.edpnbackend.domain.repository;

import io.edpn.edpnbackend.application.dto.persistence.CommodityMarketDatumEntity;

import java.util.Optional;

public interface CommodityMarketDatumRepository {
    CommodityMarketDatumEntity update(CommodityMarketDatumEntity entity);

    Optional<CommodityMarketDatumEntity> getById(CommodityMarketDatumEntity entity);

    CommodityMarketDatumEntity create(CommodityMarketDatumEntity entity);

}
