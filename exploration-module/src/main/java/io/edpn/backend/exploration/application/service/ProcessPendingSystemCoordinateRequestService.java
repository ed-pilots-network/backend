package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.MessageDto;
import io.edpn.backend.exploration.application.dto.mapper.MessageMapper;
import io.edpn.backend.exploration.application.dto.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.outgoing.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SendKafkaMessagePort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@AllArgsConstructor
@Slf4j
public class ProcessPendingSystemCoordinateRequestService implements ProcessPendingDataRequestUseCase<SystemCoordinateRequest> {

    private final static String TOPIC = "_systemCoordinatesDataResponse"; //TODO set from config

    private final LoadAllSystemCoordinateRequestPort loadAllSystemCoordinateRequestPort;
    private final LoadSystemPort loadSystemPort;
    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    private final SystemCoordinatesResponseMapper systemCoordinatesResponseMapper;
    private final MessageMapper messageMapper;
    private final ObjectMapper objectMapper;
    private final RetryTemplate retryTemplate;
    private final Executor executor;

    @Override
    @Scheduled(cron = "0 0 */12 * * *")
    public void processPending() {
        loadAllSystemCoordinateRequestPort.load().parallelStream()
                .forEach(systemCoordinateRequest -> CompletableFuture.runAsync(() -> loadSystemPort.load(systemCoordinateRequest.systemName())
                        .ifPresent(system -> {
                                    SystemCoordinatesResponse systemCoordinatesResponse = systemCoordinatesResponseMapper.map(system);
                                    String stringJson = objectMapper.valueToTree(systemCoordinatesResponse).toString();
                                    Message message = new Message(systemCoordinateRequest.requestingModule() + TOPIC, stringJson);
                                    MessageDto messageDto = messageMapper.map(message);

                                    boolean sendSuccessful = retryTemplate.execute(retryContext -> sendKafkaMessagePort.send(messageDto));
                                    if (sendSuccessful) {
                                        deleteSystemCoordinateRequestPort.delete(systemCoordinateRequest.systemName(), systemCoordinateRequest.requestingModule());
                                    }
                                }
                        ), executor));
    }
}
