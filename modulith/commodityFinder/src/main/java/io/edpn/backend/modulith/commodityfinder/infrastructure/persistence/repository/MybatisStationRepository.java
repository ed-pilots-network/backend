package io.edpn.backend.modulith.commodityfinder.infrastructure.persistence.repository;

import io.edpn.backend.modulith.commodityfinder.application.dto.persistence.StationEntity;
import io.edpn.backend.modulith.commodityfinder.domain.repository.StationRepository;
import io.edpn.backend.modulith.commodityfinder.domain.repository.SystemRepository;
import io.edpn.backend.modulith.commodityfinder.infrastructure.persistence.mappers.MarketDatumEntityMapper;
import io.edpn.backend.modulith.commodityfinder.infrastructure.persistence.mappers.StationEntityMapper;
import io.edpn.backend.modulith.util.IdGenerator;
import io.edpn.backend.modulith.util.exception.DatabaseEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class MybatisStationRepository implements StationRepository {

    private final IdGenerator idGenerator;
    private final StationEntityMapper stationEntityMapper;
    private final MarketDatumEntityMapper marketDatumEntityMapper;
    private final SystemRepository systemRepository;

    @Override
    public StationEntity findOrCreateBySystemIdAndStationName(UUID systemId, String stationName) throws DatabaseEntityNotFoundException {
        return stationEntityMapper.findBySystemIdAndStationName(systemId, stationName)
                .orElseGet(() -> {
                    StationEntity s = StationEntity.builder()
                            .system(systemRepository.findById(systemId).orElseThrow(() -> new DatabaseEntityNotFoundException("system with id: " + systemId + " could not be found")))
                            .name(stationName)
                            .build();
                    return create(s);
                });
    }

    @Override
    public StationEntity update(StationEntity entity) throws DatabaseEntityNotFoundException {
        stationEntityMapper.update(entity);

        saveMarketData(entity);

        return findById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("station with id: " + entity.getId() + " could not be found after update"));
    }

    private void saveMarketData(StationEntity entity) {
        marketDatumEntityMapper.deleteByStationId(entity.getId());
        entity.getCommodityMarketData().forEach(marketDatumEntityMapper::insert);
    }

    @Override
    public StationEntity create(StationEntity entity) throws DatabaseEntityNotFoundException {
        if (Objects.isNull(entity.getId())) {
            entity.setId(idGenerator.generateId());
        }
        stationEntityMapper.insert(entity);
        saveMarketData(entity);

        return findById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("station with id: " + entity.getId() + " could not be found after create"));
    }

    @Override
    public Optional<StationEntity> findById(UUID id) {
        return stationEntityMapper.findById(id);
    }
}
