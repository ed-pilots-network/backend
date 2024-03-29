package io.edpn.backend.trade.adapter.persistence;


import io.edpn.backend.trade.application.domain.intermodulecommunication.SystemDataRequest;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisSystemDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemEliteIdRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CreateIfNotExistsSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.ExistsSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestsPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class SystemEliteIdRequestRepository implements CreateIfNotExistsSystemEliteIdRequestPort, ExistsSystemEliteIdRequestPort, DeleteSystemEliteIdRequestPort, LoadAllSystemEliteIdRequestsPort {

    private final MybatisSystemEliteIdRequestRepository mybatisSystemEliteIdRequestRepository;
    private final MybatisSystemDataRequestEntityMapper mybatisSystemDataRequestEntityMapper;

    @Override
    public void createIfNotExists(String systemName) {
        mybatisSystemEliteIdRequestRepository.insertIfNotExists(systemName);
    }

    @Override
    public void delete(String systemName) {
        mybatisSystemEliteIdRequestRepository.delete(systemName);
    }

    @Override
    public boolean exists(String systemName) {
        return mybatisSystemEliteIdRequestRepository.exists(systemName);
    }

    @Override
    public List<SystemDataRequest> loadAll() {
        return mybatisSystemEliteIdRequestRepository.findAll().stream()
                .map(mybatisSystemDataRequestEntityMapper::map)
                .toList();
    }
}
