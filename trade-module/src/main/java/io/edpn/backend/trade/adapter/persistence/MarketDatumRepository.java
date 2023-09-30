package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.repository.MybatisMarketDatumRepository;
import io.edpn.backend.trade.application.port.outgoing.marketdatum.ExistsByStationNameAndSystemNameAndTimestampPort;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class MarketDatumRepository implements ExistsByStationNameAndSystemNameAndTimestampPort {
    
    private final MybatisMarketDatumRepository mybatisMarketDatumRepository;
    
    @Override
    public boolean exists(@NotBlank @NotNull String systemName,@NotBlank @NotNull String stationName, @NotNull LocalDateTime timestamp) {
        return mybatisMarketDatumRepository.existsByStationNameAndSystemNameAndTimestamp(systemName, stationName, timestamp);
    }
}
