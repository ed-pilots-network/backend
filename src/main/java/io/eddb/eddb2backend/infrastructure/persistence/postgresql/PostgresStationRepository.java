package io.eddb.eddb2backend.infrastructure.persistence.postgresql;

import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station.StationEntity;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgresStationRepository extends JpaRepository<StationEntity, Long> {
    
    Collection<StationEntity> findByNameContainingIgnoreCase(String name);
}
