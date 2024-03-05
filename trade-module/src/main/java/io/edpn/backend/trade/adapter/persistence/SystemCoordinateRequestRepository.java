package io.edpn.backend.trade.adapter.persistence;


import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.SystemDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CreateSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.ExistsSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestsPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class SystemCoordinateRequestRepository implements CreateSystemCoordinateRequestPort, ExistsSystemCoordinateRequestPort, DeleteSystemCoordinateRequestPort, LoadAllSystemCoordinateRequestsPort {

    private final MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository;
    private final SystemDataRequestEntityMapper systemDataRequestEntityMapper;

    @Override
    public void create(String systemName) {
        mybatisSystemCoordinateRequestRepository.insert(systemName);
    }

    @Override
    public void delete(String systemName) {
        mybatisSystemCoordinateRequestRepository.delete(systemName);
    }

    @Override
    public boolean exists(String systemName) {
        return mybatisSystemCoordinateRequestRepository.exists(systemName);
    }

    @Override
    public List<SystemDataRequest> loadAll() {
        return mybatisSystemCoordinateRequestRepository.findAll().stream()
                .map(systemDataRequestEntityMapper::map)
                .toList();
    }
}
