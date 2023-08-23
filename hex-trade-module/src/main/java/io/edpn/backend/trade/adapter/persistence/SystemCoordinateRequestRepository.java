package io.edpn.backend.trade.adapter.persistence;


import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CreateSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.ExistsSystemCoordinateRequestPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class SystemCoordinateRequestRepository implements CreateSystemCoordinateRequestPort, ExistsSystemCoordinateRequestPort, DeleteSystemCoordinateRequestPort {

    private final MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository;

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
}
