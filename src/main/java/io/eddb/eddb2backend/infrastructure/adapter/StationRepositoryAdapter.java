package io.eddb.eddb2backend.infrastructure.adapter;

import io.eddb.eddb2backend.domain.model.station.Station;
import io.eddb.eddb2backend.domain.repository.StationRepository;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.PostgresqlStationRepository;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.PostgresStationEntity;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StationRepositoryAdapter implements StationRepository {
    private final PostgresqlStationRepository jpaStationRepository;

    @Override
    public Station save(Station station) {
        PostgresStationEntity entity = PostgresStationEntity.Mapper.map(station);
        PostgresStationEntity savedEntity = jpaStationRepository.save(entity);
        return PostgresStationEntity.Mapper.map(savedEntity);
    }

    @Override
    public Optional<Station> findById(Long id) {
        return jpaStationRepository.findById(id).map(PostgresStationEntity.Mapper::map);
    }

    @Override
    public Collection<Station> findByName(String name) {
        return jpaStationRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(PostgresStationEntity.Mapper::map)
                .toList();
    }

    @Override
    public Collection<Station> findAll() {
        return jpaStationRepository.findAll().stream()
                .map(PostgresStationEntity.Mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaStationRepository.deleteById(id);
    }
}
