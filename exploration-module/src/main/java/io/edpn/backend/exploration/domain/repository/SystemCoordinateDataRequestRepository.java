package io.edpn.backend.exploration.domain.repository;

import io.edpn.backend.exploration.domain.model.SystemCoordinateDataRequest;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface SystemCoordinateDataRequestRepository {

    Optional<SystemCoordinateDataRequest> find(SystemCoordinateDataRequest entity) throws DatabaseEntityNotFoundException;

    List<SystemCoordinateDataRequest> findBySystemName(String systemName) throws DatabaseEntityNotFoundException;

    SystemCoordinateDataRequest create(SystemCoordinateDataRequest entity) throws DatabaseEntityNotFoundException;

    List<SystemCoordinateDataRequest> findAll();

    void delete(SystemCoordinateDataRequest entity);

}
