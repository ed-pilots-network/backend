package io.edpn.backend.trade.adapter.persistence;


import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemEliteIdRequestRepository;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.SystemDataRequestEntityMapper;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CreateSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.ExistsSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestsPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class SystemEliteIdRequestRepository implements CreateSystemEliteIdRequestPort, ExistsSystemEliteIdRequestPort, DeleteSystemEliteIdRequestPort, LoadAllSystemEliteIdRequestsPort {

    private final MybatisSystemEliteIdRequestRepository mybatisSystemEliteIdRequestRepository;
    private final SystemDataRequestEntityMapper systemDataRequestEntityMapper;

    @Override
    public void create(String systemName) {
        mybatisSystemEliteIdRequestRepository.insert(systemName);
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
                .map(systemDataRequestEntityMapper::map)
                .toList();
    }
}
