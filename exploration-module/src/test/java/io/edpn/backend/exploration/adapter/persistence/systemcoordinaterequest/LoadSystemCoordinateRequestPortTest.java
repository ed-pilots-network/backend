package io.edpn.backend.exploration.adapter.persistence.systemcoordinaterequest;

import io.edpn.backend.exploration.adapter.persistence.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemCoordinateRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemCoordinateRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestPort;
import io.edpn.backend.util.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadSystemCoordinateRequestPortTest {

    @Mock
    private MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository;

    @Mock
    private MybatisSystemCoordinateRequestEntityMapper mybatisSystemCoordinateRequestEntityMapper;

    private LoadSystemCoordinateRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, mybatisSystemCoordinateRequestEntityMapper);
    }


    @Test
    void create_shouldMapAndInsert() {
        SystemCoordinateRequest request = mock(SystemCoordinateRequest.class);
        when(request.systemName()).thenReturn("system");
        Module module = Module.TRADE;
        when(request.requestingModule()).thenReturn(module);
        MybatisSystemCoordinateRequestEntity entity = mock(MybatisSystemCoordinateRequestEntity.class);
        when(mybatisSystemCoordinateRequestRepository.find(module, "system")).thenReturn(Optional.of(entity));
        when(mybatisSystemCoordinateRequestEntityMapper.map(entity)).thenReturn(request);

        Optional<SystemCoordinateRequest> result = underTest.load(request);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(request));

        verify(mybatisSystemCoordinateRequestRepository).find(module, "system");
    }
}
