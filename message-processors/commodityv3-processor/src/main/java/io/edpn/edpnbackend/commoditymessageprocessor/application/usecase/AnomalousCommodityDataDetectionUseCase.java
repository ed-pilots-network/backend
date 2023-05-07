package io.edpn.edpnbackend.commoditymessageprocessor.application.usecase;

import io.edpn.edpnbackend.commoditymessageprocessor.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;

public interface AnomalousCommodityDataDetectionUseCase {
    boolean isAnomalous(HistoricStationCommodityMarketDatumEntity commodityMarketDatumEntity);
}
