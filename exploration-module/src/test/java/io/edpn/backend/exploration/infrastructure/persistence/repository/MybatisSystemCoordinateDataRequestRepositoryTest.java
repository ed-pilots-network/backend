package io.edpn.backend.exploration.infrastructure.persistence.repository;

import io.edpn.backend.exploration.domain.model.SystemCoordinateDataRequest;
import io.edpn.backend.exploration.domain.repository.SystemCoordinateDataRequestRepository;
import io.edpn.backend.exploration.infrastructure.persistence.entity.SystemCoordinateDataRequestEntity;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.entity.SystemCoordinateDataRequestMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis.SystemCoordinateDataRequestEntityMapper;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MybatisSystemCoordinateDataRequestRepositoryTest {

    @Mock
    private SystemCoordinateDataRequestMapper systemCoordinateDataRequestMapper;

    @Mock
    private SystemCoordinateDataRequestEntityMapper systemCoordinateDataRequestEntityMapper;

    private SystemCoordinateDataRequestRepository underTest;

    @BeforeEach
    void setUp() {
        underTest = new MybatisSystemCoordinateDataRequestRepository(systemCoordinateDataRequestMapper, systemCoordinateDataRequestEntityMapper);
    }

    @Test
    void shouldFindSystemCoordinateDataRequest() throws DatabaseEntityNotFoundException {
        SystemCoordinateDataRequest inputSystemCoordinateDataRequestMock = mock(SystemCoordinateDataRequest.class);
        SystemCoordinateDataRequestEntity inputSystemCoordinateDataRequestEntityMock = mock(SystemCoordinateDataRequestEntity.class);
        SystemCoordinateDataRequest outputSystemCoordinateDataRequestMock = mock(SystemCoordinateDataRequest.class);
        SystemCoordinateDataRequestEntity outputSystemCoordinateDataRequestEntityMock = mock(SystemCoordinateDataRequestEntity.class);

        when(systemCoordinateDataRequestMapper.map(inputSystemCoordinateDataRequestMock)).thenReturn(inputSystemCoordinateDataRequestEntityMock);
        when(systemCoordinateDataRequestEntityMapper.find(inputSystemCoordinateDataRequestEntityMock)).thenReturn(Optional.of(outputSystemCoordinateDataRequestEntityMock));
        when(systemCoordinateDataRequestMapper.map(outputSystemCoordinateDataRequestEntityMock)).thenReturn(outputSystemCoordinateDataRequestMock);

        Optional<SystemCoordinateDataRequest> result = underTest.find(inputSystemCoordinateDataRequestMock);

        assertThat(result.isPresent(), equalTo(true));
        assertThat(result.get(), is(outputSystemCoordinateDataRequestMock));
    }

    @Test
    void shouldFindSystemCoordinateDataRequestNotFound() throws DatabaseEntityNotFoundException {
        SystemCoordinateDataRequest inputSystemCoordinateDataRequestMock = mock(SystemCoordinateDataRequest.class);
        SystemCoordinateDataRequestEntity inputSystemCoordinateDataRequestEntityMock = mock(SystemCoordinateDataRequestEntity.class);

        when(systemCoordinateDataRequestMapper.map(inputSystemCoordinateDataRequestMock)).thenReturn(inputSystemCoordinateDataRequestEntityMock);
        when(systemCoordinateDataRequestEntityMapper.find(inputSystemCoordinateDataRequestEntityMock)).thenReturn(Optional.empty());

        Optional<SystemCoordinateDataRequest> result = underTest.find(inputSystemCoordinateDataRequestMock);

        assertThat(result.isPresent(), equalTo(false));
    }

    @Test
    void shouldFindBySystemName() throws DatabaseEntityNotFoundException {
        String systemName = "systemName";
        SystemCoordinateDataRequestEntity systemCoordinateDataRequestEntityMock = mock(SystemCoordinateDataRequestEntity.class);
        List<SystemCoordinateDataRequestEntity> entityList = Collections.singletonList(systemCoordinateDataRequestEntityMock);
        SystemCoordinateDataRequest systemCoordinateDataRequest = mock(SystemCoordinateDataRequest.class);

        when(systemCoordinateDataRequestEntityMapper.findBySystemName(systemName)).thenReturn(entityList);
        when(systemCoordinateDataRequestMapper.map(systemCoordinateDataRequestEntityMock)).thenReturn(systemCoordinateDataRequest);

        List<SystemCoordinateDataRequest> result = underTest.findBySystemName(systemName);

        assertThat(result, hasSize(1));
        assertThat(result, contains(systemCoordinateDataRequest));
    }

    @Test
    void shouldFindBySystemNameNotFound() throws DatabaseEntityNotFoundException {
        String systemName = "SystemName";
        List<SystemCoordinateDataRequestEntity> entityList = Collections.emptyList();

        when(systemCoordinateDataRequestEntityMapper.findBySystemName(systemName)).thenReturn(entityList);

        List<SystemCoordinateDataRequest> result = underTest.findBySystemName(systemName);

        assertThat(result, empty());
    }

    @Test
    void shouldCreateSystemCoordinateDataRequest() throws DatabaseEntityNotFoundException {
        SystemCoordinateDataRequest inputSystemCoordinateDataRequest = mock(SystemCoordinateDataRequest.class);
        SystemCoordinateDataRequestEntity inputSystemCoordinateDataRequestEntity = mock(SystemCoordinateDataRequestEntity.class);
        SystemCoordinateDataRequestEntity outputSystemCoordinateDataRequestEntity = mock(SystemCoordinateDataRequestEntity.class);
        SystemCoordinateDataRequest outputSystemCoordinateDataRequest = mock(SystemCoordinateDataRequest.class);

        when(systemCoordinateDataRequestMapper.map(inputSystemCoordinateDataRequest)).thenReturn(inputSystemCoordinateDataRequestEntity);
        when(systemCoordinateDataRequestEntityMapper.find(inputSystemCoordinateDataRequestEntity)).thenReturn(Optional.of(outputSystemCoordinateDataRequestEntity));
        when(systemCoordinateDataRequestMapper.map(outputSystemCoordinateDataRequestEntity)).thenReturn(outputSystemCoordinateDataRequest);

        SystemCoordinateDataRequest result = underTest.create(inputSystemCoordinateDataRequest);

        assertThat(result, is(outputSystemCoordinateDataRequest));
    }

    @Test
    void shouldFindAll() {
        SystemCoordinateDataRequestEntity inputSystemCoordinateDataRequestEntity = mock(SystemCoordinateDataRequestEntity.class);
        List<SystemCoordinateDataRequestEntity> entityList = Collections.singletonList(inputSystemCoordinateDataRequestEntity);
        SystemCoordinateDataRequest outputSystemCoordinateDataRequest = mock(SystemCoordinateDataRequest.class);

        when(systemCoordinateDataRequestEntityMapper.findAll()).thenReturn(entityList);
        when(systemCoordinateDataRequestMapper.map(inputSystemCoordinateDataRequestEntity)).thenReturn(outputSystemCoordinateDataRequest);

        List<SystemCoordinateDataRequest> result = underTest.findAll();

        assertThat(result, hasSize(1));
        assertThat(result, contains(outputSystemCoordinateDataRequest));
    }

    @Test
    void shouldDelete() {
        SystemCoordinateDataRequest inputSystemCoordinateDataRequest = mock(SystemCoordinateDataRequest.class);
        SystemCoordinateDataRequestEntity inputSystemCoordinateDataRequestEntity = mock(SystemCoordinateDataRequestEntity.class);

        when(systemCoordinateDataRequestMapper.map(inputSystemCoordinateDataRequest)).thenReturn(inputSystemCoordinateDataRequestEntity);

        underTest.delete(inputSystemCoordinateDataRequest);

        verify(systemCoordinateDataRequestEntityMapper).delete(any());
    }
}
