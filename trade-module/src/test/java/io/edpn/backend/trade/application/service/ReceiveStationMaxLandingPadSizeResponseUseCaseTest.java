package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.station.CreateOrLoadStationPort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.CreateStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.DeleteStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.ExistsStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.LoadAllStationLandingPadSizeRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.system.CreateOrLoadSystemPort;
import io.edpn.backend.util.IdGenerator;
import java.util.concurrent.Executor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.support.RetryTemplate;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiveStationMaxLandingPadSizeResponseUseCaseTest {
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private LoadStationsByFilterPort loadStationsByFilterPort;
    @Mock
    private LoadAllStationLandingPadSizeRequestsPort loadAllStationLandingPadSizeRequestsPort;
    @Mock
    private CreateOrLoadSystemPort createOrLoadSystemPort;
    @Mock
    private CreateOrLoadStationPort createOrLoadStationPort;
    @Mock
    private ExistsStationLandingPadSizeRequestPort existsStationLandingPadSizeRequestPort;
    @Mock
    private CreateStationLandingPadSizeRequestPort createStationLandingPadSizeRequestPort;
    @Mock
    private DeleteStationLandingPadSizeRequestPort deleteStationLandingPadSizeRequestPort;
    @Mock
    private UpdateStationPort updateStationPort;
    @Mock
    private SendKafkaMessagePort sendKafkaMessagePort;
    @Mock
    private RetryTemplate retryTemplate;
    @Mock
    private Executor executor;
    @Mock
    private ObjectMapper objectMapper;

    private ReceiveKafkaMessageUseCase<StationMaxLandingPadSizeResponse> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new StationLandingPadSizeInterModuleCommunicationService(
                idGenerator,
                loadStationsByFilterPort,
                loadAllStationLandingPadSizeRequestsPort,
                createOrLoadSystemPort,
                createOrLoadStationPort,
                existsStationLandingPadSizeRequestPort,
                createStationLandingPadSizeRequestPort,
                deleteStationLandingPadSizeRequestPort,
                updateStationPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper
        );
    }

    @Test
    public void shouldReceiveStationMaxLandingPadSizeResponse() {
        StationMaxLandingPadSizeResponse message =
                new StationMaxLandingPadSizeResponse("station", "system", "LARGE");

        System system = mock(System.class);
        when(createOrLoadSystemPort.createOrLoad(argThat(argument -> argument.name().equals("system")))).thenReturn(system);

        Station station = mock(Station.class);
        when(createOrLoadStationPort.createOrLoad(argThat(argument -> argument.system().equals(system) && argument.name().equals("station")))).thenReturn(station);
        when(station.withMaxLandingPadSize(LandingPadSize.LARGE)).thenReturn(station);

        underTest.receive(message);

        verify(updateStationPort, times(1)).update(station);
        verify(deleteStationLandingPadSizeRequestPort, times(1)).delete("system", "station");
    }
}
