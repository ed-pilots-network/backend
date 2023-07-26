package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.domain.model.RequestDataMessage;
import io.edpn.backend.trade.domain.service.KafkaSenderService;
import io.edpn.backend.trade.domain.service.SendDataRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class DefaultSendStationDataRequestService implements SendDataRequestService<StationDataRequest> {

    private final KafkaSenderService<RequestDataMessage> kafkaSenderService;
    private final ObjectMapper objectMapper;

    @Override
    public void send(StationDataRequest stationDataRequest, String topic) {
        CompletableFuture.runAsync(() -> {
            RequestDataMessage requestDataMessage = RequestDataMessage.builder()
                    .topic(topic)
                    .message(objectMapper.valueToTree(stationDataRequest).toString())
                    .build();

            kafkaSenderService.sendToKafka(requestDataMessage);
        });
    }
}
