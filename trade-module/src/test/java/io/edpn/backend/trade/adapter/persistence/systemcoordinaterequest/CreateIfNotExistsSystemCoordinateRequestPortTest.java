package io.edpn.backend.trade.adapter.persistence.systemcoordinaterequest;

import io.edpn.backend.trade.adapter.persistence.SystemCoordinateRequestRepository;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisSystemDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CreateIfNotExistsSystemCoordinateRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateIfNotExistsSystemCoordinateRequestPortTest {

    @Mock
    private MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository;
    @Mock
    private MybatisSystemDataRequestEntityMapper mybatisSystemDataRequestEntityMapper;

    private CreateIfNotExistsSystemCoordinateRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, mybatisSystemDataRequestEntityMapper);
    }

    @Test
    public void testCreate() {
        String systemName = "someName";

        underTest.createIfNotExists(systemName);

        verify(mybatisSystemCoordinateRequestRepository).insertIfNotExists(systemName);
    }


}
