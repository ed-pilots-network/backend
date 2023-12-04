package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.StationMaxLandingPadSizeResponseMapper;
import io.edpn.backend.exploration.application.dto.web.object.MessageDto;
import io.edpn.backend.exploration.application.dto.web.object.mapper.MessageDtoMapper;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.station.LoadStationPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.DeleteStationMaxLandingPadSizeRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.LoadStationMaxLandingPadSizeRequestByIdentifierPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.StationMaxLandingPadSizeResponseSender;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import io.edpn.backend.util.ConcurrencyUtil;
import io.edpn.backend.util.Topic;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;

@RequiredArgsConstructor
@Slf4j
public class StationMaxLandingPadSizeResponseSenderService implements StationMaxLandingPadSizeResponseSender {

    private final LoadStationPort loadStationPort;
    private final LoadStationMaxLandingPadSizeRequestByIdentifierPort loadStationMaxLandingPadSizeRequestByIdentifierPort;
    private final DeleteStationMaxLandingPadSizeRequestPort deleteStationMaxLandingPadSizeRequestPort;
    private final SendMessagePort sendMessagePort;
    private final StationMaxLandingPadSizeResponseMapper stationMaxLandingPadSizeResponseMapper;
    private final MessageDtoMapper messageMapper;
    private final ObjectMapper objectMapper;
    private final RetryTemplate retryTemplate;
    private final ExecutorService executorService;

    @Override
    public void sendResponsesForStation(String systemName, String stationName) {
        loadStationMaxLandingPadSizeRequestByIdentifierPort.loadByIdentifier(systemName, stationName).parallelStream()
                .forEach(stationMaxLandingPadSizeRequest ->
                        executorService.submit(ConcurrencyUtil.errorHandlingWrapper(() -> {
                                    try {
                                        Station station = loadStationPort.load(systemName, stationName).orElseThrow(() -> new IllegalStateException("System with name %s not found when application event for it was triggered".formatted(systemName)));
                                        StationMaxLandingPadSizeResponse stationMaxLandingPadSizeResponse = stationMaxLandingPadSizeResponseMapper.map(station);
                                        String stringJson = objectMapper.writeValueAsString(stationMaxLandingPadSizeResponse);
                                        String topic = Topic.Response.STATION_MAX_LANDING_PAD_SIZE.getFormattedTopicName(stationMaxLandingPadSizeRequest.requestingModule());
                                        Message message = new Message(topic, stringJson);
                                        MessageDto messageDto = messageMapper.map(message);

                                        boolean sendSuccessful = retryTemplate.execute(retryContext -> sendMessagePort.send(messageDto));
                                        if (sendSuccessful) {
                                            deleteStationMaxLandingPadSizeRequestPort.delete(systemName, stationName, stationMaxLandingPadSizeRequest.requestingModule());
                                        }
                                    } catch (JsonProcessingException jpe) {
                                        throw new RuntimeException(jpe);
                                    }
                                },
                                exception -> log.error("Error while processing stationMaxLandingPadSizeResponse for station {} in system: {}", stationName, systemName, exception))));
    }
}
