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
        return StationEntity.Mapper.map(station)
                .map(jpaPostgresStationRepository::save)
                .flatMap(StationEntity.Mapper::map)
                .orElseThrow(() -> new RuntimeException("could not save Station")); // TODO create better exception
    }

    @Override
    public Optional<Station> findById(Long id) {
        return jpaPostgresStationRepository.findById(id)
                .flatMap(StationEntity.Mapper::map);
    }

    @Override
    public Collection<Station> findByName(String name) {
        return jpaPostgresStationRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(StationEntity.Mapper::map)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaPostgresStationRepository.deleteById(id);
    }
}
