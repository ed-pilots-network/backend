package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.station.LoadStationPort;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.DeleteStationArrivalDistanceRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.LoadStationArrivalDistanceRequestByIdentifierPort;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.StationArrivalDistanceResponseSender;
import io.edpn.backend.exploration.application.domain.intermodulecommunication.StationArrivalDistanceResponse;
import io.edpn.backend.util.ConcurrencyUtil;
import io.edpn.backend.util.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;

import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@Slf4j
public class StationArrivalDistanceResponseSenderService implements StationArrivalDistanceResponseSender {
    private final LoadStationPort loadStationPort;
    private final LoadStationArrivalDistanceRequestByIdentifierPort loadStationArrivalDistanceRequestByIdentifierPort;
    private final DeleteStationArrivalDistanceRequestPort deleteStationArrivalDistanceRequestPort;
    private final SendMessagePort sendMessagePort;
    private final ObjectMapper objectMapper;
    private final RetryTemplate retryTemplate;
    private final ExecutorService executorService;


    @Override
    public void sendResponsesForStation(String systemName, String stationName) {
        loadStationArrivalDistanceRequestByIdentifierPort.loadByIdentifier(systemName, stationName).parallelStream()
                .forEach(stationArrivalDistanceRequest ->
                        executorService.submit(ConcurrencyUtil.errorHandlingWrapper(() -> {
                                    try {
                                        Station station = loadStationPort.load(systemName, stationName).orElseThrow(() -> new IllegalStateException("System with name %s not found when application event for it was triggered".formatted(systemName)));
                                        StationArrivalDistanceResponse stationArrivalDistanceResponse = StationArrivalDistanceResponse.from(station);
                                        String stringJson = objectMapper.writeValueAsString(stationArrivalDistanceResponse);
                                        String topic = Topic.Response.STATION_ARRIVAL_DISTANCE.getFormattedTopicName(stationArrivalDistanceRequest.requestingModule());
                                        Message message = new Message(topic, stringJson);

                                        boolean sendSuccessful = retryTemplate.execute(retryContext -> sendMessagePort.send(message));
                                        if (sendSuccessful) {
                                            deleteStationArrivalDistanceRequestPort.delete(systemName, stationName, stationArrivalDistanceRequest.requestingModule());
                                        }
                                    } catch (JsonProcessingException jpe) {
                                        throw new RuntimeException(jpe);
                                    }
                                },
                                exception -> log.error("Error while processing stationArrivalDistanceResponse for station {} in system: {}", stationName, systemName, exception))));


    }
}

