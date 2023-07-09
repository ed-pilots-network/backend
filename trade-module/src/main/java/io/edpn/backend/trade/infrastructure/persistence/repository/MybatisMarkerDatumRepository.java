package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.repository.MarketDatumRepository;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.MarketDatumEntityMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class MybatisMarkerDatumRepository implements MarketDatumRepository {

    private final MarketDatumEntityMapper marketDatumEntityMapper;


    @Override
    public boolean existsByStationNameAndSystemNameAndTimestamp(@NotNull @NotBlank String systemName, @NotNull @NotBlank String stationName, @NotNull LocalDateTime timestamp) {
        return marketDatumEntityMapper.existsByStationNameAndSystemNameAndTimestamp(systemName, stationName, timestamp);
    }
}
