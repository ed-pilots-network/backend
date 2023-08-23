package io.edpn.backend.exploration.adapter.persistence.systemcoordinaterequest;

import io.edpn.backend.exploration.adapter.persistence.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemCoordinateRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemCoordinateRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.CreateSystemCoordinateRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateSystemCoordinateRequestPortTest {

    @Mock
    private MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository;

    @Mock
    private MybatisSystemCoordinateRequestEntityMapper mybatisSystemCoordinateRequestEntityMapper;

    private CreateSystemCoordinateRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, mybatisSystemCoordinateRequestEntityMapper);
    }

    @Test
    void create_shouldMapAndInsert() {

        SystemCoordinateRequest request = mock(SystemCoordinateRequest.class);
        MybatisSystemCoordinateRequestEntity entity = mock(MybatisSystemCoordinateRequestEntity.class);
        when(mybatisSystemCoordinateRequestEntityMapper.map(request)).thenReturn(entity);


        underTest.create(request);


        verify(mybatisSystemCoordinateRequestRepository).insert(entity);
    }
}