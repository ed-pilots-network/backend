package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.SystemCoordinateRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.SystemCoordinateRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemCoordinateRequestBySystemNamePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadSystemCoordinateRequestBySystemNamePortTest {

    @Mock
    private MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository;

    @Mock
    private SystemCoordinateRequestEntityMapper systemCoordinateRequestEntityMapper;

    private LoadSystemCoordinateRequestBySystemNamePort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, systemCoordinateRequestEntityMapper);
    }

    @Test
    void load_withSystemName_shouldFindBySystemNameAndMap() {
        // Given
        String systemName = "system";
        SystemCoordinateRequestEntity systemCoordinateRequestEntity = mock(SystemCoordinateRequestEntity.class);
        List<SystemCoordinateRequestEntity> entities = List.of(systemCoordinateRequestEntity);
        SystemCoordinateRequest mapped = mock(SystemCoordinateRequest.class);
        when(mybatisSystemCoordinateRequestRepository.findBySystemName(systemName)).thenReturn(entities);
        when(systemCoordinateRequestEntityMapper.map(systemCoordinateRequestEntity)).thenReturn(mapped);

        // When
        List<SystemCoordinateRequest> result = underTest.load(systemName);

        // Then
        assertThat(result, contains(mapped));
        verify(mybatisSystemCoordinateRequestRepository).findBySystemName(systemName);
    }

}