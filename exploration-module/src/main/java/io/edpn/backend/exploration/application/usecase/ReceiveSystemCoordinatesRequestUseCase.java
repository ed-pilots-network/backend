package io.edpn.backend.exploration.application.usecase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.domain.model.RequestDataMessage;
import io.edpn.backend.exploration.domain.model.System;
import io.edpn.backend.exploration.domain.model.SystemCoordinateDataRequest;
import io.edpn.backend.exploration.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.exploration.domain.repository.SystemCoordinateDataRequestRepository;
import io.edpn.backend.exploration.domain.repository.SystemRepository;
import io.edpn.backend.exploration.domain.usecase.ReceiveDataRequestUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class ReceiveSystemCoordinatesRequestUseCase implements ReceiveDataRequestUseCase<SystemDataRequest> {

    private final SystemRepository systemRepository;
    private final RequestDataMessageRepository requestDataMessageRepository;
    private final SystemCoordinateDataRequestRepository systemCoordinateDataRequestRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void receive(SystemDataRequest message, String requestingModule) {
        CompletableFuture.supplyAsync(() -> systemRepository.findByName(message.getSystemName()))
                .thenAcceptAsync(optionalSystem ->
                        optionalSystem.ifPresentOrElse(
                                system -> sendCoordinatesToKafka(system, requestingModule),
                                () -> createSystemCoordinateDataRequest(message, requestingModule)
                        )
                );
    }

    private void sendCoordinatesToKafka(System system, String requestingModule) {
        CompletableFuture.runAsync(() -> {
            SystemCoordinatesResponse systemCoordinatesResponse = createSystemCoordinatesResponse(system);

            JsonNode jsonNode = objectMapper.valueToTree(systemCoordinatesResponse);

            RequestDataMessage requestDataMessage = RequestDataMessage.builder()
                    .topic(requestingModule + "StationArrivalDistanceDataRequest")
                    .message(jsonNode)
                    .build();

            requestDataMessageRepository.sendToKafka(requestDataMessage);
        });
    }

    private void createSystemCoordinateDataRequest(SystemDataRequest message, String requestingModule) {
        systemCoordinateDataRequestRepository.create(
                SystemCoordinateDataRequest.builder()
                        .requestingModule(requestingModule)
                        .systemName(message.getSystemName())
                        .build()
        );
    }

    private SystemCoordinatesResponse createSystemCoordinatesResponse(System system) {
        SystemCoordinatesResponse systemCoordinatesResponse = new SystemCoordinatesResponse();
        systemCoordinatesResponse.setSystemName(system.getName());
        systemCoordinatesResponse.setXCoordinate(system.getXCoordinate());
        systemCoordinatesResponse.setYCoordinate(system.getYCoordinate());
        systemCoordinatesResponse.setZCoordinate(system.getZCoordinate());
        return systemCoordinatesResponse;
    }
}
