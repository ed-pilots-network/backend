package io.eddb.eddb2backend.infrastructure.adapter;

import io.eddb.eddb2backend.domain.model.station.Station;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.PostgresStationRepository;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station.StationEntity;
import java.util.Collection;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StationRepositoryAdapter implements io.eddb.eddb2backend.domain.repository.StationRepository {
    private final PostgresStationRepository jpaPostgresStationRepository;

    @Override
    public Station save(Station station) {
        StationEntity entity = StationEntity.Mapper.map(station);
        StationEntity savedEntity = jpaPostgresStationRepository.save(entity);
        return StationEntity.Mapper.map(savedEntity);
    }

    @Override
    public Optional<Station> findById(Long id) {
        return jpaPostgresStationRepository.findById(id).map(StationEntity.Mapper::map);
    }

    @Override
    public Collection<Station> findByName(String name) {
        return jpaPostgresStationRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(StationEntity.Mapper::map)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaPostgresStationRepository.deleteById(id);
    }
}
