package io.eddb.eddb2backend.infrastructure.persistence;

import io.eddb.eddb2backend.domain.model.Station;
import io.eddb.eddb2backend.domain.repository.StationRepository;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.PostgresqlStationRepository;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.StationEntity;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StationRepositoryAdapter implements StationRepository {
    private final PostgresqlStationRepository jpaStationRepository;

    @Override
    public Station save(Station station) {
        StationEntity entity = toEntity(station);
        StationEntity savedEntity = jpaStationRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Station> findById(Long id) {
        return jpaStationRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Collection<Station> findByName(String name) {
        return jpaStationRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Collection<Station> findAll() {
        return jpaStationRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaStationRepository.deleteById(id);
    }

    private StationEntity toEntity(Station station) {
        StationEntity entity = new StationEntity();
        entity.setId(station.id());
        entity.setName(station.name());
        return entity;
    }

    private Station toDomain(StationEntity entity) {
        return new Station(entity.getId(), entity.getName());
    }
}
