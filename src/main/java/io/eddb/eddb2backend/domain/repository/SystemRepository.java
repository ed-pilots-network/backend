package io.eddb.eddb2backend.domain.repository;

import java.util.Collection;
import java.util.Optional;

public interface SystemRepository {
    System save(System system);
    
    Optional<System> findById(Long id);
    
    Collection<System> findByName(String name);
    
    void deleteById(Long id);
}
