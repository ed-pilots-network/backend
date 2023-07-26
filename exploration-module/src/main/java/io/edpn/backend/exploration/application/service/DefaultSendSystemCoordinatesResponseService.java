package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.domain.model.RequestDataMessage;
import io.edpn.backend.exploration.domain.service.KafkaSenderService;
import io.edpn.backend.exploration.domain.service.SendDataResponseService;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class DefaultSendSystemCoordinatesResponseService implements SendDataResponseService<SystemCoordinatesResponse> {

    private final KafkaSenderService<RequestDataMessage> kafkaSenderService;
    private final ObjectMapper objectMapper;

    @Override
    public void send(SystemCoordinatesResponse systemCoordinatesResponse, String requestingModule) {
        CompletableFuture.runAsync(() -> {
            RequestDataMessage requestDataMessage = RequestDataMessage.builder()
                    .topic(requestingModule + "_systemCoordinatesDataResponse")
                    .message(objectMapper.valueToTree(systemCoordinatesResponse).toString())
                    .build();

            kafkaSenderService.sendToKafka(requestDataMessage);
        });
    }
}
