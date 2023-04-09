package io.eddb.eddb2backend.infrastructure.adapter;

import io.eddb.eddb2backend.domain.model.station.Station;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.StationRepository;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station.StationEntity;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StationRepositoryAdapter implements io.eddb.eddb2backend.domain.repository.StationRepository {
    private final StationRepository jpaStationRepository;

    @Override
    public Station save(Station station) {
        StationEntity entity = StationEntity.Mapper.map(station);
        StationEntity savedEntity = jpaStationRepository.save(entity);
        return StationEntity.Mapper.map(savedEntity);
    }

    @Override
    public Optional<Station> findById(Long id) {
        return jpaStationRepository.findById(id).map(StationEntity.Mapper::map);
    }

    @Override
    public Collection<Station> findByName(String name) {
        return jpaStationRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(StationEntity.Mapper::map)
                .toList();
    }

    @Override
    public Collection<Station> findAll() {
        return jpaStationRepository.findAll().stream()
                .map(StationEntity.Mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaStationRepository.deleteById(id);
    }
}
