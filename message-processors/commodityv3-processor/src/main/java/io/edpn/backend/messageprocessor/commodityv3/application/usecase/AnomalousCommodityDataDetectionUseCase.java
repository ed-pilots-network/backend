package io.edpn.backend.messageprocessor.commodityv3.application.usecase;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;

public interface AnomalousCommodityDataDetectionUseCase {
    boolean isAnomalous(HistoricStationCommodityMarketDatumEntity commodityMarketDatumEntity);
}
