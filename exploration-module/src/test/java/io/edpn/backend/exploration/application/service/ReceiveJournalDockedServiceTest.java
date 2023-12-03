package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.LandingPadSize;
import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.port.outgoing.station.SaveOrUpdateStationPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.StationMaxLandingPadSizeResponseSender;
import io.edpn.backend.exploration.application.port.outgoing.system.SaveOrUpdateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.SystemCoordinatesResponseSender;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.SystemEliteIdResponseSender;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.journal.DockedMessage;
import io.edpn.backend.util.IdGenerator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiveJournalDockedServiceTest {
    public static final java.util.UUID UUID = java.util.UUID.randomUUID();

    @Mock
    private IdGenerator idGenerator;
    @Mock
    private SaveOrUpdateSystemPort saveOrUpdateSystemPort;
    @Mock
    private SaveOrUpdateStationPort saveOrUpdateStationPort;
    @Mock
    private SystemCoordinatesResponseSender systemCoordinatesResponseSender;
    @Mock
    private SystemEliteIdResponseSender systemEliteIdResponseSender;
    @Mock
    private StationMaxLandingPadSizeResponseSender stationMaxLandingPadSizeResponseSender;
    @Mock
    private ExecutorService executorService;

    private ReceiveJournalDockedService underTest;

    @BeforeEach
    void setUp() {
        when(idGenerator.generateId()).thenReturn(UUID);
        underTest = new ReceiveJournalDockedService(
                idGenerator,
                systemCoordinatesResponseSender,
                systemEliteIdResponseSender,
                stationMaxLandingPadSizeResponseSender,
                saveOrUpdateStationPort,
                saveOrUpdateSystemPort,
                executorService
        );
    }

    @SneakyThrows
    @Test
    void testReceiveAndSendResponses() {
        String stationName = "station";
        String systemName = "system";
        long marketId = 123L;
        long systemEliteId = 1L;
        double xCoord = 1.0;
        double yCoord = 1.0;
        double zCoord = 1.0;
        String stationType = "stationType";
        DockedMessage.V1 message = mock(DockedMessage.V1.class);
        DockedMessage.V1.Payload payload = mock(DockedMessage.V1.Payload.class);
        System system = mock(System.class);
        Station station = mock(Station.class);
        when(system.name()).thenReturn(systemName);
        when(station.system()).thenReturn(system);
        when(station.name()).thenReturn(stationName);
        when(message.message()).thenReturn(payload);
        when(message.messageTimeStamp()).thenReturn(LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        when(payload.distanceFromStar()).thenReturn(1000.0);
        when(payload.landingPads()).thenReturn(new DockedMessage.V1.Payload.LandingPads(1, 2, 3));
        when(payload.marketId()).thenReturn(marketId);
        when(payload.starPosition()).thenReturn(new Double[]{xCoord, yCoord, zCoord});
        when(payload.starSystem()).thenReturn(systemName);
        when(payload.stationEconomies()).thenReturn(List.of(
                new DockedMessage.V1.Payload.Economy("economy1", 0.9),
                new DockedMessage.V1.Payload.Economy("economy2", 0.1)
        ));
        when(payload.stationEconomy()).thenReturn("economy1");
        when(payload.stationGovernment()).thenReturn("government");
        when(payload.stationName()).thenReturn(stationName);
        when(payload.stationServices()).thenReturn(List.of("service1", "service2"));
        when(payload.stationType()).thenReturn(stationType);
        when(payload.systemAddress()).thenReturn(systemEliteId);
        when(payload.horizons()).thenReturn(true);
        when(payload.odyssey()).thenReturn(true);
        when(saveOrUpdateSystemPort.saveOrUpdate(argThat(argument ->
                argument.id().equals(UUID) &&
                        argument.eliteId().equals(systemEliteId) &&
                        argument.name().equals(systemName) &&
                        Objects.isNull(argument.primaryStarClass()) &&
                        argument.coordinate().x().equals(xCoord) &&
                        argument.coordinate().y().equals(yCoord) &&
                        argument.coordinate().z().equals(zCoord)
        ))).thenReturn(system);
        when(saveOrUpdateStationPort.saveOrUpdate(argThat(argument ->
                argument.id().equals(UUID) &&
                        argument.marketId().equals(marketId) &&
                        argument.name().equals(stationName) &&
                        argument.type().equals(stationType) &&
                        argument.distanceFromStar().equals(1000.0) &&
                        argument.system().equals(system) &&
                        argument.landingPads().equals(Map.of(LandingPadSize.LARGE, 1, LandingPadSize.MEDIUM, 2, LandingPadSize.SMALL, 3)) &&
                        argument.economies().equals(Map.of("economy1", 0.9, "economy2", 0.1)) &&
                        argument.economy().equals("economy1") &&
                        argument.government().equals("government") &&
                        argument.odyssey().equals(true) &&
                        argument.horizons().equals(true) &&
                        argument.updatedAt().equals(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
        ))).thenReturn(station);
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);

        underTest.receive(message);

        // Verify runnable
        verify(executorService, times(3)).submit(runnableArgumentCaptor.capture());
        runnableArgumentCaptor.getAllValues().forEach(Runnable::run);

        verify(systemCoordinatesResponseSender).sendResponsesForSystem(systemName);
        verify(systemEliteIdResponseSender).sendResponsesForSystem(systemName);
        verify(stationMaxLandingPadSizeResponseSender).sendResponsesForStation(systemName, stationName);
    }
}
