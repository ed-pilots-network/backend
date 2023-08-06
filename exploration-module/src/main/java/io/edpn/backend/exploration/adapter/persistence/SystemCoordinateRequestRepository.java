package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemCoordinateRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemCoordinateRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.util.Module;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class SystemCoordinateRequestRepository implements CreateSystemCoordinateRequestPort, LoadSystemCoordinateRequestBySystemNamePort, LoadAllSystemCoordinateRequestPort, DeleteSystemCoordinateRequestPort {

    private final MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository;
    private final MybatisSystemCoordinateRequestEntityMapper mybatisSystemCoordinateRequestEntityMapper;

    @Override
    public void create(SystemCoordinateRequest systemCoordinateRequest) {
        mybatisSystemCoordinateRequestRepository.insert(mybatisSystemCoordinateRequestEntityMapper.map(systemCoordinateRequest));
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
}
