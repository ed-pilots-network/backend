package io.edpn.backend.exploration.adapter.persistence.systemcoordinaterequest;

import io.edpn.backend.exploration.adapter.persistence.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemCoordinateRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemCoordinateRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadAllSystemCoordinateRequestPortTest {

    @Mock
    private MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository;

    @Mock
    private MybatisSystemCoordinateRequestEntityMapper mybatisSystemCoordinateRequestEntityMapper;

    private LoadAllSystemCoordinateRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, mybatisSystemCoordinateRequestEntityMapper);
    }

    @Test
    void load_shouldFindAllAndMap() {

        MybatisSystemCoordinateRequestEntity mybatisSystemCoordinateRequestEntity = mock(MybatisSystemCoordinateRequestEntity.class);
        List<MybatisSystemCoordinateRequestEntity> entities = List.of(mybatisSystemCoordinateRequestEntity);
        SystemCoordinateRequest mapped = mock(SystemCoordinateRequest.class);
        when(mybatisSystemCoordinateRequestRepository.findAll()).thenReturn(entities);
        when(mybatisSystemCoordinateRequestEntityMapper.map(mybatisSystemCoordinateRequestEntity)).thenReturn(mapped);


        List<SystemCoordinateRequest> result = underTest.loadAll();


        assertThat(result, contains(mapped));
        verify(mybatisSystemCoordinateRequestRepository).findAll();
    }

}