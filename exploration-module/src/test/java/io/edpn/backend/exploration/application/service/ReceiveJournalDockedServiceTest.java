package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.port.outgoing.station.SaveOrUpdateStationPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.StationMaxLandingPadSizeResponseSender;
import io.edpn.backend.exploration.application.port.outgoing.system.SaveOrUpdateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.SystemCoordinatesResponseSender;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.SystemEliteIdResponseSender;
import io.edpn.backend.util.IdGenerator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiveJournalDockedServiceTest {
    public static final String UUID = "902cef08-8409-4a7b-bbd1-c9c583b28454";

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
        when(idGenerator.generateId()).thenReturn(java.util.UUID.fromString(UUID));
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
        fail("TODO");
    }
}
