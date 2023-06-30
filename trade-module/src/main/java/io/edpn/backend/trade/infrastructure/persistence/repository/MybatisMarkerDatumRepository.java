package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.model.MarketDatum;
import io.edpn.backend.trade.domain.repository.MarketDatumRepository;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.MarketDatumMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.MarketDatumEntityMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisMarkerDatumRepository implements MarketDatumRepository {

    private final MarketDatumEntityMapper marketDatumEntityMapper;
    private final MarketDatumMapper marketDatumMapper;


    @Override
    public boolean existsByStationNameAndSystemNameAndTimestamp(String systemName, String stationName, LocalDateTime timestamp) {
        return marketDatumEntityMapper.existsByStationNameAndSystemNameAndTimestamp(systemName, stationName, timestamp);
    }
    
    @Override
    public List<MarketDatum> findAllOrderByDistance(UUID commodityId) {
        return marketDatumEntityMapper.
                findAllOrderByDistance(commodityId).stream()
                .map(marketDatumMapper::map)
                .collect(Collectors.toList());
    }
}
