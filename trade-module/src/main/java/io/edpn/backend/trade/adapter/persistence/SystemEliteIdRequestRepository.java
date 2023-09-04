package io.edpn.backend.trade.adapter.persistence;


import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemEliteIdRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CreateSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.ExistsSystemEliteIdRequestPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class SystemEliteIdRequestRepository implements CreateSystemEliteIdRequestPort, ExistsSystemEliteIdRequestPort, DeleteSystemEliteIdRequestPort {

    private final MybatisSystemEliteIdRequestRepository mybatisSystemEliteIdRequestRepository;

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
}
