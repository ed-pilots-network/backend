package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import io.edpn.backend.exploration.application.domain.intermodulecommunication.StationMaxLandingPadSizeResponse;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.station.LoadStationPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.DeleteStationMaxLandingPadSizeRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.LoadStationMaxLandingPadSizeRequestByIdentifierPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.StationMaxLandingPadSizeResponseSender;
import io.edpn.backend.util.Module;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StationMaxLandingPadSizeResponseSenderTest {

    @Mock
    private LoadStationPort loadStationPort;
    @Mock
    private LoadStationMaxLandingPadSizeRequestByIdentifierPort loadStationMaxLandingPadSizeRequestByIdentifierPort;
    @Mock
    private DeleteStationMaxLandingPadSizeRequestPort deleteStationMaxLandingPadSizeRequestPort;
    @Mock
    private SendMessagePort sendMessagePort;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private RetryTemplate retryTemplate;
    @Mock
    private ExecutorService executorService;

    private StationMaxLandingPadSizeResponseSender underTest;

    @BeforeEach
    void setUp() {
        underTest = new StationMaxLandingPadSizeResponseSenderService(
                loadStationPort,
                loadStationMaxLandingPadSizeRequestByIdentifierPort,
                deleteStationMaxLandingPadSizeRequestPort,
                sendMessagePort,
                objectMapper,
                retryTemplate,
                executorService);
    }

    @SneakyThrows
    @Test
    void onEvent_shouldProcessPendingRequest() {
        try (MockedStatic<StationMaxLandingPadSizeResponse> stationMaxLandingPadSizeResponseMockedStatic = mockStatic(StationMaxLandingPadSizeResponse.class)) {
            String systemName = "systemName";
            String stationName = "stationName";
            Module module = mock(Module.class);
            when(module.getName()).thenReturn("module");
            StationMaxLandingPadSizeRequest request1 = mock(StationMaxLandingPadSizeRequest.class);
            when(request1.requestingModule()).thenReturn(module);
            when(loadStationMaxLandingPadSizeRequestByIdentifierPort.loadByIdentifier(systemName, stationName)).thenReturn(List.of(request1));
            ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);
            Station mockStation = mock(Station.class);
            when(loadStationPort.load(systemName, stationName)).thenReturn(Optional.of(mockStation));
            StationMaxLandingPadSizeResponse mockStationMaxLandingPadSizeResponse = mock(StationMaxLandingPadSizeResponse.class);
            stationMaxLandingPadSizeResponseMockedStatic.when(() -> StationMaxLandingPadSizeResponse.from(mockStation)).thenReturn(mockStationMaxLandingPadSizeResponse);
            when(objectMapper.writeValueAsString(mockStationMaxLandingPadSizeResponse)).thenReturn("JSON_STRING");
            Message message = new Message("module_stationMaxLandingPadSizeResponse", "JSON_STRING");
            when(sendMessagePort.send(message)).thenReturn(true);
            doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

            underTest.sendResponsesForStation(systemName, stationName);

            verify(executorService).submit(runnableArgumentCaptor.capture());

            // Verify runnable
            runnableArgumentCaptor.getAllValues().forEach(Runnable::run);
            verify(sendMessagePort).send(message);
            verify(deleteStationMaxLandingPadSizeRequestPort).delete(systemName, stationName, module);
        }
    }

    @SneakyThrows
    @Test
    void onEvent_shouldThrowErrorWhenStationNotFound() {
        String systemName = "systemName";
        String stationName = "stationName";
        StationMaxLandingPadSizeRequest request = mock(StationMaxLandingPadSizeRequest.class);
        when(loadStationMaxLandingPadSizeRequestByIdentifierPort.loadByIdentifier(systemName, stationName)).thenReturn(List.of(request));
        when(loadStationPort.load(systemName, stationName)).thenReturn(Optional.empty());
        ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);


        underTest.sendResponsesForStation(systemName, stationName);
        verify(executorService).submit(runnableArgumentCaptor.capture());
        runnableArgumentCaptor.getAllValues().forEach(Runnable::run);

        verify(sendMessagePort, never()).send(any());
        verify(deleteStationMaxLandingPadSizeRequestPort, never()).delete(any(), any(), any());
    }

    @SneakyThrows
    @Test
    void onEvent_shouldNotDeleteRequestWhenSendFails() {
        try (MockedStatic<StationMaxLandingPadSizeResponse> stationMaxLandingPadSizeResponseMockedStatic = mockStatic(StationMaxLandingPadSizeResponse.class)) {
            String systemName = "systemName";
            String stationName = "stationName";
            StationMaxLandingPadSizeRequest request1 = mock(StationMaxLandingPadSizeRequest.class);
            Module module = mock(Module.class);
            when(module.getName()).thenReturn("module");
            when(request1.requestingModule()).thenReturn(module);
            when(loadStationMaxLandingPadSizeRequestByIdentifierPort.loadByIdentifier(systemName, stationName)).thenReturn(List.of(request1));
            ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);
            Station mockStation = mock(Station.class);
            when(loadStationPort.load(systemName, stationName)).thenReturn(Optional.of(mockStation));
            StationMaxLandingPadSizeResponse mockStationMaxLandingPadSizeResponse = mock(StationMaxLandingPadSizeResponse.class);
            stationMaxLandingPadSizeResponseMockedStatic.when(() -> StationMaxLandingPadSizeResponse.from(mockStation)).thenReturn(mockStationMaxLandingPadSizeResponse);
            when(objectMapper.writeValueAsString(mockStationMaxLandingPadSizeResponse)).thenReturn("JSON_STRING");
            Message message = new Message("module_stationMaxLandingPadSizeResponse", "JSON_STRING");
            when(sendMessagePort.send(message)).thenReturn(false);
            doAnswer(invocation -> ((RetryCallback<?, ?>) invocation.getArgument(0)).doWithRetry(null)).when(retryTemplate).execute(any());

            underTest.sendResponsesForStation(systemName, stationName);

            verify(executorService).submit(runnableArgumentCaptor.capture());

            // Verify runnable
            runnableArgumentCaptor.getAllValues().forEach(Runnable::run);
            verify(deleteStationMaxLandingPadSizeRequestPort, never()).delete(systemName, stationName, module);
        }
    }
}
