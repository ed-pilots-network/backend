package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.filter.FindStationFilter;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.CreateStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.RequestMissingStationRequireOdysseyUseCase;
import io.edpn.backend.util.Module;
import io.edpn.backend.util.Topic;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@AllArgsConstructor
@Slf4j
public class RequestMissingStationRequireOdysseyService implements RequestMissingStationRequireOdysseyUseCase {

    public static final FindStationFilter FIND_STATION_FILTER = FindStationFilter.builder()
            .hasRequiredOdyssey(false)
            .build();

    private final LoadStationsByFilterPort loadStationsByFilterPort;
    private final CreateStationRequireOdysseyRequestPort createStationRequireOdysseyRequestPort;
    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final RetryTemplate retryTemplate;
    private final Executor executor;
    private final ObjectMapper objectMapper;
    private final MessageMapper messageMapper;

    @Override
    @Scheduled(cron = "0 */12 * * *")
    public void requestMissing() {
        loadStationsByFilterPort.loadByFilter(FIND_STATION_FILTER).parallelStream()
                .forEach(station ->
                        CompletableFuture.runAsync(() -> {
                            StationDataRequest stationDataRequest = new StationDataRequest(Module.TRADE, station.getName(), station.getSystem().getName());

                            JsonNode jsonNode = objectMapper.valueToTree(stationDataRequest);

                            Message message = Message.builder()
                                    .topic(Topic.Request.STATION_REQUIRE_ODYSSEY.getTopicName())
                                    .message(jsonNode.toString())
                                    .build();

                            boolean sendSuccessful = retryTemplate.execute(retryContext ->
                                    sendKafkaMessagePort.send(messageMapper.map(message)));
                            if (sendSuccessful) {
                                createStationRequireOdysseyRequestPort.create(station.getSystem().getName(), station.getName());
                            }
                        }, executor));
    }
}
