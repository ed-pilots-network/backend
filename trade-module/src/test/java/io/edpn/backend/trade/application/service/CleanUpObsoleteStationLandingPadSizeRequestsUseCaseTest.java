package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindStationFilter;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.DeleteStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.LoadAllStationLandingPadSizeRequestsPort;
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
public class CleanUpObsoleteStationLandingPadSizeRequestsUseCaseTest {

    @Mock
    private LoadStationsByFilterPort loadStationsByFilterPort;

    @Mock
    private LoadAllStationLandingPadSizeRequestsPort loadAllStationLandingPadSizeRequestsPort;

    @Mock
    private DeleteStationLandingPadSizeRequestPort deleteStationLandingPadSizeRequestPort;

    private CleanUpObsoleteStationLandingPadSizeRequestsService underTest;

    @BeforeEach
    public void setUp() {
        underTest = new CleanUpObsoleteStationLandingPadSizeRequestsService(loadStationsByFilterPort, loadAllStationLandingPadSizeRequestsPort, deleteStationLandingPadSizeRequestPort);
    }

    @Test
    public void testFindStationsFilter() {
        FindStationFilter findStationFilter = FindStationFilter.builder()
                .hasLandingPadSize(false)
                .build();

        assertThat(CleanUpObsoleteStationLandingPadSizeRequestsService.FIND_STATION_FILTER, equalTo(findStationFilter));
    }

    @Test
    public void testCleanUpObsolete_NoOpenRequests() {
        when(loadAllStationLandingPadSizeRequestsPort.loadAll()).thenReturn(Collections.emptyList());
        when(loadStationsByFilterPort.loadByFilter(any())).thenReturn(Collections.emptyList());

        underTest.cleanUpObsolete();

        verify(deleteStationLandingPadSizeRequestPort, never()).delete(any(), any());
    }

    @Test
    public void testCleanUpObsolete_MoreMissingItems() {
        StationDataRequest request1 = mock(StationDataRequest.class);
        when(request1.stationName()).thenReturn("home");
        when(request1.systemName()).thenReturn("Alpha");
        StationDataRequest request2 = mock(StationDataRequest.class);
        when(request2.stationName()).thenReturn("away");
        when(request2.systemName()).thenReturn("Beta");

        System system3 = mock(System.class);
        when(system3.getName()).thenReturn("Alpha");
        Station station3 = mock(Station.class);
        when(station3.getName()).thenReturn("home");
        when(station3.getSystem()).thenReturn(system3);

        System system2 = mock(System.class);
        when(system2.getName()).thenReturn("Beta");
        Station station2 = mock(Station.class);
        when(station2.getName()).thenReturn("away");
        when(station2.getSystem()).thenReturn(system2);

        Station station1 = mock(Station.class);
        when(station1.getName()).thenReturn("far");

        when(loadAllStationLandingPadSizeRequestsPort.loadAll()).thenReturn(List.of(request1, request2));
        when(loadStationsByFilterPort.loadByFilter(any())).thenReturn(List.of(station1, station2, station3));

        underTest.cleanUpObsolete();

        verify(deleteStationLandingPadSizeRequestPort, never()).delete(any(), any());
    }

    @Test
    public void testCleanUpObsolete_LessMissingItems() {
        StationDataRequest request1 = mock(StationDataRequest.class);
        when(request1.stationName()).thenReturn("home");
        when(request1.systemName()).thenReturn("Alpha");
        StationDataRequest request2 = mock(StationDataRequest.class);
        when(request2.stationName()).thenReturn("away");
        when(request2.systemName()).thenReturn("Beta");

        System system1 = mock(System.class);
        when(system1.getName()).thenReturn("Alpha");
        Station station1 = mock(Station.class);
        when(station1.getName()).thenReturn("home");
        when(station1.getSystem()).thenReturn(system1);

        when(loadAllStationLandingPadSizeRequestsPort.loadAll()).thenReturn(List.of(request1, request2));
        when(loadStationsByFilterPort.loadByFilter(any())).thenReturn(List.of(station1));

        underTest.cleanUpObsolete();

        verify(deleteStationLandingPadSizeRequestPort).delete("Beta", "away");
    }

    @Test
    public void testCleanUpObsolete_TheSameMissingItems() {

        StationDataRequest request1 = mock(StationDataRequest.class);
        when(request1.stationName()).thenReturn("home");
        when(request1.systemName()).thenReturn("Alpha");
        StationDataRequest request2 = mock(StationDataRequest.class);
        when(request2.stationName()).thenReturn("away");
        when(request2.systemName()).thenReturn("Beta");

        System system1 = mock(System.class);
        when(system1.getName()).thenReturn("Alpha");
        Station station1 = mock(Station.class);
        when(station1.getName()).thenReturn("home");
        when(station1.getSystem()).thenReturn(system1);

        System system2 = mock(System.class);
        when(system2.getName()).thenReturn("Beta");
        Station station2 = mock(Station.class);
        when(station2.getName()).thenReturn("away");
        when(station2.getSystem()).thenReturn(system2);

        when(loadAllStationLandingPadSizeRequestsPort.loadAll()).thenReturn(List.of(request1, request2));
        when(loadStationsByFilterPort.loadByFilter(any())).thenReturn(List.of(station1, station2));

        underTest.cleanUpObsolete();

        verify(deleteStationLandingPadSizeRequestPort, never()).delete(any(), any());
    }
}
