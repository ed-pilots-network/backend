package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CleanUpObsoleteSystemCoordinateRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestsPort;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CleanUpObsoleteSystemCoordinateRequestsUseCaseTest {

    @Mock
    private LoadSystemsByFilterPort loadSystemsByFilterPort;

    @Mock
    private LoadAllSystemCoordinateRequestsPort loadAllSystemCoordinateRequestsPort;

    @Mock
    private DeleteSystemCoordinateRequestPort deleteSystemRequireOdysseyRequestPort;

    private CleanUpObsoleteSystemCoordinateRequestsUseCase underTest;

    @BeforeEach
    public void setUp() {
        underTest = new CleanUpObsoleteSystemCoordinateRequestsService(loadSystemsByFilterPort, loadAllSystemCoordinateRequestsPort, deleteSystemRequireOdysseyRequestPort);
    }

    @Test
    public void testFindSystemsFilter() {
        FindSystemFilter findSystemFilter = FindSystemFilter.builder()
                .hasCoordinates(false)
                .build();

        assertThat(CleanUpObsoleteSystemCoordinateRequestsService.FIND_SYSTEM_FILTER, equalTo(findSystemFilter));
    }

    @Test
    public void testCleanUpObsolete_NoOpenRequests() {
        when(loadAllSystemCoordinateRequestsPort.loadAll()).thenReturn(Collections.emptyList());
        when(loadSystemsByFilterPort.loadByFilter(any())).thenReturn(Collections.emptyList());

        underTest.cleanUpObsolete();

        verify(deleteSystemRequireOdysseyRequestPort, never()).delete(any());
    }

    @Test
    public void testCleanUpObsolete_MoreMissingItems() {
        SystemDataRequest request1 = mock(SystemDataRequest.class);
        when(request1.systemName()).thenReturn("Alpha");
        SystemDataRequest request2 = mock(SystemDataRequest.class);
        when(request2.systemName()).thenReturn("Beta");

        System system3 = mock(System.class);
        when(system3.getName()).thenReturn("Alpha");

        System system2 = mock(System.class);
        when(system2.getName()).thenReturn("Beta");

        System system1 = mock(System.class);
        when(system1.getName()).thenReturn("Gamma");

        when(loadAllSystemCoordinateRequestsPort.loadAll()).thenReturn(List.of(request1, request2));
        when(loadSystemsByFilterPort.loadByFilter(any())).thenReturn(List.of(system1, system2, system3));

        underTest.cleanUpObsolete();

        verify(deleteSystemRequireOdysseyRequestPort, never()).delete(any());
    }

    @Test
    public void testCleanUpObsolete_LessMissingItems() {
        SystemDataRequest request1 = mock(SystemDataRequest.class);
        when(request1.systemName()).thenReturn("Alpha");
        SystemDataRequest request2 = mock(SystemDataRequest.class);
        when(request2.systemName()).thenReturn("Beta");

        System system1 = mock(System.class);
        when(system1.getName()).thenReturn("Alpha");

        when(loadAllSystemCoordinateRequestsPort.loadAll()).thenReturn(List.of(request1, request2));
        when(loadSystemsByFilterPort.loadByFilter(any())).thenReturn(List.of(system1));

        underTest.cleanUpObsolete();

        verify(deleteSystemRequireOdysseyRequestPort).delete("Beta");
    }

    @Test
    public void testCleanUpObsolete_TheSameMissingItems() {

        SystemDataRequest request1 = mock(SystemDataRequest.class);
        when(request1.systemName()).thenReturn("Alpha");
        SystemDataRequest request2 = mock(SystemDataRequest.class);
        when(request2.systemName()).thenReturn("Beta");

        System system1 = mock(System.class);
        when(system1.getName()).thenReturn("Alpha");

        System system2 = mock(System.class);
        when(system2.getName()).thenReturn("Beta");

        when(loadAllSystemCoordinateRequestsPort.loadAll()).thenReturn(List.of(request1, request2));
        when(loadSystemsByFilterPort.loadByFilter(any())).thenReturn(List.of(system1, system2));

        underTest.cleanUpObsolete();

        verify(deleteSystemRequireOdysseyRequestPort, never()).delete(any());
    }
}
