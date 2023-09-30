package io.edpn.backend.exploration.adapter.persistence.systemeliteidrequest;

import io.edpn.backend.exploration.adapter.persistence.MybatisSystemEliteIdRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemEliteIdRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEliteIdRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemEliteIdRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.CreateIfNotExistsSystemEliteIdRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateIfNotExistsSystemEliteIdRequestPortTest {

    @Mock
    private MybatisSystemEliteIdRequestRepository mybatisSystemEliteIdRequestRepository;

    @Mock
    private MybatisSystemEliteIdRequestEntityMapper mybatisSystemEliteIdRequestEntityMapper;

    private CreateIfNotExistsSystemEliteIdRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemEliteIdRequestRepository(mybatisSystemEliteIdRequestRepository, mybatisSystemEliteIdRequestEntityMapper);
    }

    @Test
    void create_shouldMapAndInsert() {

        SystemEliteIdRequest request = mock(SystemEliteIdRequest.class);
        MybatisSystemEliteIdRequestEntity entity = mock(MybatisSystemEliteIdRequestEntity.class);
        when(mybatisSystemEliteIdRequestEntityMapper.map(request)).thenReturn(entity);


        underTest.createIfNotExists(request);


        verify(mybatisSystemEliteIdRequestRepository).insertIfNotExists(entity);
    }
}