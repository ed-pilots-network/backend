package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisMarketDatumRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRepository;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindStationFilter;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceFindStationFilterMapper;
import io.edpn.backend.trade.application.port.outgoing.station.CreateStationPort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadOrCreateBySystemAndStationNamePort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationByIdPort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class StationRepository implements CreateStationPort, LoadOrCreateBySystemAndStationNamePort, LoadStationByIdPort, UpdateStationPort, LoadStationsByFilterPort {

    private final IdGenerator idGenerator;
    private final StationEntityMapper<MybatisStationEntity> mybatisStationEntityMapper;
    private final MybatisStationRepository mybatisStationRepository;
    private final MybatisMarketDatumRepository mybatisMarketDatumRepository;
    private final PersistenceFindStationFilterMapper mybatisPersistenceFindStationFilterMapper;

    @Override
    public Station create(Station station) throws DatabaseEntityNotFoundException {
        var entity = mybatisStationEntityMapper.map(station);
        if (Objects.isNull(entity.getId())) {
            entity.setId(idGenerator.generateId());
        }
        mybatisStationRepository.insert(entity);
        saveMarketData(entity);

        return loadById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("station with id: " + entity.getId() + " could not be found after create"));
    }

    //TODO: refactor for skipping iteration
    private void saveMarketData(MybatisStationEntity stationEntity) {
        stationEntity.getMarketData().forEach(mybatisMarketDatumEntity -> {
            if (mybatisMarketDatumRepository.findById(stationEntity.getId(), mybatisMarketDatumEntity.getCommodity().getId(), mybatisMarketDatumEntity.getTimestamp()).isEmpty()) {
                mybatisMarketDatumRepository.insert(stationEntity.getId(), mybatisMarketDatumEntity);
            } else {
                log.warn("Did not save marketDatum because of record with identical key already exists");
            }
        });
    }

    @Override
    public Station loadOrCreateBySystemAndStationName(System system, String stationName) {
        return mybatisStationRepository.findBySystemIdAndStationName(system.getId(), stationName)
                .map(mybatisStationEntityMapper::map)
                .orElseGet(() -> {
                    Station s = Station.builder()
                            .system(system)
                            .name(stationName)
                            .build();
                    return create(s);
                });
    }

    @Override
    public Optional<Station> loadById(UUID uuid) {
        return mybatisStationRepository.findById(uuid)
                .map(mybatisStationEntityMapper::map);
    }

    @Override
    public Station update(Station station) {
        var entity = mybatisStationEntityMapper.map(station);
        mybatisStationRepository.update(entity);

        saveMarketData(entity);

        return loadById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("station with id: " + entity.getId() + " could not be found after update"));
    }

    @Override
    public List<Station> loadByFilter(FindStationFilter findStationFilter) {
        return mybatisStationRepository.findByFilter(mybatisPersistenceFindStationFilterMapper.map(findStationFilter))
                .stream()
                .map(mybatisStationEntityMapper::map)
                .toList();
    }
}
