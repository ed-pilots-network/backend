package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.domain.KafkaMessage;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.outgoing.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SendKafkaMessagePort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Slf4j
public class ProcessPendingSystemCoordinateRequestService implements ProcessPendingDataRequestUseCase<SystemCoordinateRequest> {

    private final static String TOPIC = "_systemCoordinatesDataResponse"; //TODO set from config

    private final LoadAllSystemCoordinateRequestPort loadAllSystemCoordinateRequestPort;
    private final LoadSystemPort loadSystemPort;
    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    private final SystemCoordinatesResponseMapper systemCoordinatesResponseMapper;
    private final ObjectMapper objectMapper;

    @Override
    @Scheduled(cron = "0 0 */12 * * *")
    public void processPending() {
        loadAllSystemCoordinateRequestPort.load().parallelStream()
                .forEach(systemCoordinateRequest -> CompletableFuture.runAsync(() -> loadSystemPort.load(systemCoordinateRequest.systemName())
                        .ifPresent(system -> {
                                    SystemCoordinatesResponse systemCoordinatesResponse = systemCoordinatesResponseMapper.map(system);
                                    String stringJson = objectMapper.valueToTree(systemCoordinatesResponse).toString();
                                    KafkaMessage kafkaMessage = new KafkaMessage(systemCoordinateRequest.requestingModule() + TOPIC, stringJson);

                                    if (sendKafkaMessagePort.send(kafkaMessage)) {  //TODO replace with retry template
                                        deleteSystemCoordinateRequestPort.delete(system.getName(), systemCoordinateRequest.requestingModule());
                                    }
                                }
                        )));
    }
}
