package io.edpn.backend.commodityfinder.domain.repository;

import java.time.LocalDateTime;

public interface MarketDatumRepository {

    boolean existsByStationNameAndSystemNameAndTimestamp(String systemName, String stationName, LocalDateTime timestamp);

}
