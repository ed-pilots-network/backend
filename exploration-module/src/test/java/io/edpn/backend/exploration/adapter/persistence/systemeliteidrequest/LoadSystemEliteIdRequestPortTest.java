package io.edpn.backend.exploration.adapter.persistence.systemeliteidrequest;

import io.edpn.backend.exploration.adapter.persistence.MybatisSystemEliteIdRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemEliteIdRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEliteIdRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemEliteIdRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadSystemEliteIdRequestPort;
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
public class LoadSystemEliteIdRequestPortTest {

    @Mock
    private MybatisSystemEliteIdRequestRepository mybatisSystemEliteIdRequestRepository;

    @Mock
    private MybatisSystemEliteIdRequestEntityMapper mybatisSystemEliteIdRequestEntityMapper;

    private LoadSystemEliteIdRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemEliteIdRequestRepository(mybatisSystemEliteIdRequestRepository, mybatisSystemEliteIdRequestEntityMapper);
    }


    @Test
    void create_shouldMapAndInsert() {
        SystemEliteIdRequest request = mock(SystemEliteIdRequest.class);
        when(request.getSystemName()).thenReturn("system");
        Module module = Module.TRADE;
        when(request.getRequestingModule()).thenReturn(module);
        MybatisSystemEliteIdRequestEntity entity = mock(MybatisSystemEliteIdRequestEntity.class);
        when(mybatisSystemEliteIdRequestRepository.find(module, "system")).thenReturn(Optional.of(entity));
        when(mybatisSystemEliteIdRequestEntityMapper.map(entity)).thenReturn(request);

        Optional<SystemEliteIdRequest> result = underTest.load(request);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(request));

        verify(mybatisSystemEliteIdRequestRepository).find(module, "system");
    }
}
