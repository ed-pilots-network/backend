package io.eddb.eddb2backend.infrastructure.persistence.postgresql;

import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.PostgresStationEntity;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgresqlStationRepository extends JpaRepository<PostgresStationEntity, Long> {

    Collection<PostgresStationEntity> findByNameContainingIgnoreCase(String name);
}
