package io.edpn.backend.trade.domain.repository;

import io.edpn.backend.trade.domain.model.MarketDatum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MarketDatumRepository {

    boolean existsByStationNameAndSystemNameAndTimestamp(String systemName, String stationName, LocalDateTime timestamp);
    
    //TODO: replace Id with appropriate fields/body
    List<MarketDatum> findAllOrderByDistance(UUID commodityId);

}
