package io.edpn.edpnbackend.application.usecase;

import io.edpn.edpnbackend.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;

public interface AnomalousCommodityDataDetectionUseCase {
    boolean isAnomalous(HistoricStationCommodityMarketDatumEntity commodityMarketDatumEntity);
}
