package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.application.domain.intermodulecommunication.SystemDataRequest;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.system.CreateOrLoadSystemPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CleanUpObsoleteSystemEliteIdRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CreateIfNotExistsSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.ExistsSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestsPort;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import io.edpn.backend.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.support.RetryTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CleanUpObsoleteSystemEliteIdRequestsUseCaseTest {

    @Mock
    private IdGenerator idGenerator;
    @Mock
    private LoadSystemsByFilterPort loadSystemsByFilterPort;
    @Mock
    private LoadAllSystemEliteIdRequestsPort loadAllSystemEliteIdRequestsPort;
    @Mock
    private CreateOrLoadSystemPort createOrLoadSystemPort;
    @Mock
    private ExistsSystemEliteIdRequestPort existsSystemEliteIdRequestPort;
    @Mock
    private CreateIfNotExistsSystemEliteIdRequestPort createIfNotExistsSystemEliteIdRequestPort;
    @Mock
    private DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort;
    @Mock
    private UpdateSystemPort updateSystemPort;
    @Mock
    private SendKafkaMessagePort sendKafkaMessagePort;
    @Mock
    private RetryTemplate retryTemplate;
    @Mock
    private Executor executor;
    @Mock
    private ObjectMapper objectMapper;

    private CleanUpObsoleteSystemEliteIdRequestsUseCase underTest;

    @BeforeEach
    public void setUp() {
        underTest = new SystemEliteIdInterModuleCommunicationService(
                idGenerator,
                loadSystemsByFilterPort,
                loadAllSystemEliteIdRequestsPort,
                createOrLoadSystemPort,
                existsSystemEliteIdRequestPort,
                createIfNotExistsSystemEliteIdRequestPort,
                deleteSystemEliteIdRequestPort,
                updateSystemPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper
        );
    }

    @Test
    public void testFindSystemsFilter() {
        FindSystemFilter findSystemFilter = FindSystemFilter.builder()
                .hasEliteId(false)
                .build();

        assertThat(SystemEliteIdInterModuleCommunicationService.FIND_SYSTEM_FILTER, equalTo(findSystemFilter));
    }

    @Test
    public void testCleanUpObsolete_NoOpenRequests() {
        when(loadAllSystemEliteIdRequestsPort.loadAll()).thenReturn(Collections.emptyList());
        when(loadSystemsByFilterPort.loadByFilter(any())).thenReturn(Collections.emptyList());

        underTest.cleanUpObsolete();

        verify(deleteSystemEliteIdRequestPort, never()).delete(any());
    }

    @Test
    public void testCleanUpObsolete_MoreMissingItems() {
        SystemDataRequest request1 = mock(SystemDataRequest.class);
        when(request1.systemName()).thenReturn("Alpha");
        SystemDataRequest request2 = mock(SystemDataRequest.class);
        when(request2.systemName()).thenReturn("Beta");

        System system3 = mock(System.class);
        when(system3.name()).thenReturn("Alpha");

        System system2 = mock(System.class);
        when(system2.name()).thenReturn("Beta");

        System system1 = mock(System.class);
        when(system1.name()).thenReturn("Gamma");

        when(loadAllSystemEliteIdRequestsPort.loadAll()).thenReturn(List.of(request1, request2));
        when(loadSystemsByFilterPort.loadByFilter(any())).thenReturn(List.of(system1, system2, system3));

        underTest.cleanUpObsolete();

        verify(deleteSystemEliteIdRequestPort, never()).delete(any());
    }

    @Test
    public void testCleanUpObsolete_LessMissingItems() {
        SystemDataRequest request1 = mock(SystemDataRequest.class);
        when(request1.systemName()).thenReturn("Alpha");
        SystemDataRequest request2 = mock(SystemDataRequest.class);
        when(request2.systemName()).thenReturn("Beta");

        System system1 = mock(System.class);
        when(system1.name()).thenReturn("Alpha");

        when(loadAllSystemEliteIdRequestsPort.loadAll()).thenReturn(List.of(request1, request2));
        when(loadSystemsByFilterPort.loadByFilter(any())).thenReturn(List.of(system1));

        underTest.cleanUpObsolete();

        verify(deleteSystemEliteIdRequestPort).delete("Beta");
    }

    @Test
    public void testCleanUpObsolete_TheSameMissingItems() {

        SystemDataRequest request1 = mock(SystemDataRequest.class);
        when(request1.systemName()).thenReturn("Alpha");
        SystemDataRequest request2 = mock(SystemDataRequest.class);
        when(request2.systemName()).thenReturn("Beta");

        System system1 = mock(System.class);
        when(system1.name()).thenReturn("Alpha");

        System system2 = mock(System.class);
        when(system2.name()).thenReturn("Beta");

        when(loadAllSystemEliteIdRequestsPort.loadAll()).thenReturn(List.of(request1, request2));
        when(loadSystemsByFilterPort.loadByFilter(any())).thenReturn(List.of(system1, system2));

        underTest.cleanUpObsolete();

        verify(deleteSystemEliteIdRequestPort, never()).delete(any());
    }
}
