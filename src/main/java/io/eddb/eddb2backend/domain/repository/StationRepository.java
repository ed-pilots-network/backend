package io.eddb.eddb2backend.domain.repository;

import io.eddb.eddb2backend.domain.model.station.Station;
import java.util.Collection;
import java.util.Optional;

public interface StationRepository {
    Station save(Station station);

    Optional<Station> findById(Long id);
    Collection<Station> findByName(String id);

    Collection<Station> findAll();

    void deleteById(Long id);
}
