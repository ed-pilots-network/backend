package io.edpn.backend.exploration.infrastructure.persistence.repository;

import io.edpn.backend.exploration.domain.model.SystemCoordinateDataRequest;
import io.edpn.backend.exploration.domain.repository.SystemCoordinateDataRequestRepository;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.entity.SystemCoordinateDataRequestMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis.SystemCoordinateDataRequestEntityMapper;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisSystemCoordinateDataRequestRepository implements SystemCoordinateDataRequestRepository {

    private final SystemCoordinateDataRequestMapper systemCoordinateDataRequestMapper;
    private final SystemCoordinateDataRequestEntityMapper systemCoordinateDataRequestEntityMapper;

    @Override
    public Optional<SystemCoordinateDataRequest> find(SystemCoordinateDataRequest systemCoordinateDataRequest) throws DatabaseEntityNotFoundException {
        return systemCoordinateDataRequestEntityMapper.find(systemCoordinateDataRequestMapper.map(systemCoordinateDataRequest))
                .map(systemCoordinateDataRequestMapper::map);
    }

    @Override
    public SystemCoordinateDataRequest create(SystemCoordinateDataRequest systemCoordinateDataRequest) throws DatabaseEntityNotFoundException {
        var entity = systemCoordinateDataRequestMapper.map(systemCoordinateDataRequest);

        systemCoordinateDataRequestEntityMapper.insert(entity);
        return find(systemCoordinateDataRequest)
                .orElseThrow(() -> new DatabaseEntityNotFoundException("SystemCoordinateDataRequest '" + systemCoordinateDataRequest + "' could not be found after create"));
    }

    @Override
    public List<SystemCoordinateDataRequest> findAll() {
        return systemCoordinateDataRequestEntityMapper.findAll().stream()
                .map(systemCoordinateDataRequestMapper::map)
                .toList();
    }

    @Override
    public void delete(SystemCoordinateDataRequest systemCoordinateDataRequest) {
        systemCoordinateDataRequestEntityMapper.delete(systemCoordinateDataRequestMapper.map(systemCoordinateDataRequest));
    }
}
