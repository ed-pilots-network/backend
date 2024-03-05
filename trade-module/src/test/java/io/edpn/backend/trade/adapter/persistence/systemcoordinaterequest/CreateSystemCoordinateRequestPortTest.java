package io.edpn.backend.trade.adapter.persistence.systemcoordinaterequest;

import io.edpn.backend.trade.adapter.persistence.SystemCoordinateRequestRepository;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.SystemDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CreateSystemCoordinateRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateSystemCoordinateRequestPortTest {

    @Mock
    private MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository;
    @Mock
    private SystemDataRequestEntityMapper systemDataRequestEntityMapper;

    private CreateSystemCoordinateRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, systemDataRequestEntityMapper);
    }

    @Test
    public void testCreate() {
        String systemName = "someName";

        underTest.create(systemName);

        verify(mybatisSystemCoordinateRequestRepository).insert(systemName);
    }


}
