package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.SystemCoordinateRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.SystemCoordinateRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemCoordinateRequestPort;
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
    private SystemCoordinateRequestEntityMapper systemCoordinateRequestEntityMapper;

    private CreateSystemCoordinateRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, systemCoordinateRequestEntityMapper);
    }

    @Test
    void create_shouldMapAndInsert() {

        SystemCoordinateRequest request = mock(SystemCoordinateRequest.class);
        SystemCoordinateRequestEntity entity = mock(SystemCoordinateRequestEntity.class);
        when(systemCoordinateRequestEntityMapper.map(request)).thenReturn(entity);


        underTest.create(request);


        verify(mybatisSystemCoordinateRequestRepository).insert(entity);
    }
}