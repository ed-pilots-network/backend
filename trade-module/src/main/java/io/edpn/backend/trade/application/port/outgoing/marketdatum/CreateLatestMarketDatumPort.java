package io.edpn.backend.trade.application.port.outgoing.marketdatum;

import io.edpn.backend.trade.application.domain.MarketDatum;

import java.util.UUID;

public interface CreateLatestMarketDatumPort {

    void insertOrUpdate(UUID stationId, MarketDatum marketDatum);
}
