package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.SystemCoordinateRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.SystemCoordinateRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemCoordinateRequestBySystemNamePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class SystemCoordinateRequestRepository implements CreateSystemCoordinateRequestPort, LoadSystemCoordinateRequestBySystemNamePort, LoadAllSystemCoordinateRequestPort, DeleteSystemCoordinateRequestPort {

    private final MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository;
    private final SystemCoordinateRequestEntityMapper systemCoordinateRequestEntityMapper;

    @Override
    public void create(SystemCoordinateRequest systemCoordinateRequest) {
        mybatisSystemCoordinateRequestRepository.insert(systemCoordinateRequestEntityMapper.map(systemCoordinateRequest));
    }

    @Override
    public void delete(String systemName, String requestingModule) {
        mybatisSystemCoordinateRequestRepository.delete(new SystemCoordinateRequestEntity(systemName, requestingModule));
    }

    @Override
    public List<SystemCoordinateRequest> load(String systemName) {
        return mybatisSystemCoordinateRequestRepository.findBySystemName(systemName).stream()
                .map(systemCoordinateRequestEntityMapper::map)
                .toList();
    }

    @Override
    public List<SystemCoordinateRequest> load() {
        return mybatisSystemCoordinateRequestRepository.findAll().stream()
                .map(systemCoordinateRequestEntityMapper::map)
                .toList();
    }
}
