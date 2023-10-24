package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemCoordinateRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemCoordinateRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.CreateIfNotExistsSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestPort;
import io.edpn.backend.util.Module;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class SystemCoordinateRequestRepository implements CreateIfNotExistsSystemCoordinateRequestPort, LoadSystemCoordinateRequestPort, LoadSystemCoordinateRequestBySystemNamePort, LoadAllSystemCoordinateRequestPort, DeleteSystemCoordinateRequestPort {

    private final MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository;
    private final MybatisSystemCoordinateRequestEntityMapper mybatisSystemCoordinateRequestEntityMapper;

    @Override
    public void createIfNotExists(SystemCoordinateRequest systemCoordinateRequest) {
        mybatisSystemCoordinateRequestRepository.insertIfNotExists(mybatisSystemCoordinateRequestEntityMapper.map(systemCoordinateRequest));
    }

    @Override
    public void delete(String systemName, Module requestingModule) {
        mybatisSystemCoordinateRequestRepository.delete(new MybatisSystemCoordinateRequestEntity(systemName, requestingModule));
    }

    @Override
    public List<SystemCoordinateRequest> loadByName(String systemName) {
        return mybatisSystemCoordinateRequestRepository.findBySystemName(systemName).stream()
                .map(mybatisSystemCoordinateRequestEntityMapper::map)
                .toList();
    }

    @Override
    public List<SystemCoordinateRequest> loadAll() {
        return mybatisSystemCoordinateRequestRepository.findAll().stream()
                .map(mybatisSystemCoordinateRequestEntityMapper::map)
                .toList();
    }

    @Override
    public Optional<SystemCoordinateRequest> load(SystemCoordinateRequest systemCoordinateRequest) {
        return mybatisSystemCoordinateRequestRepository.find(systemCoordinateRequest.getRequestingModule(), systemCoordinateRequest.getSystemName())
                .map(mybatisSystemCoordinateRequestEntityMapper::map);
    }
}
