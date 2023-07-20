package io.edpn.backend.exploration.application.usecase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.domain.model.RequestDataMessage;
import io.edpn.backend.exploration.domain.model.SystemCoordinateDataRequest;
import io.edpn.backend.exploration.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.exploration.domain.repository.SystemCoordinateDataRequestRepository;
import io.edpn.backend.exploration.domain.repository.SystemRepository;
import io.edpn.backend.exploration.domain.usecase.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.domain.usecase.ReceiveDataRequestUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ReceiveSystemCoordinatesRequestUseCase implements ReceiveDataRequestUseCase<SystemDataRequest>, ProcessPendingDataRequestUseCase<SystemDataRequest> {

    private final SystemRepository systemRepository;
    private final RequestDataMessageRepository requestDataMessageRepository;
    private final SystemCoordinateDataRequestRepository systemCoordinateDataRequestRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void receive(SystemDataRequest message, String requestingModule) {
        SystemCoordinateDataRequest systemCoordinateDataRequest = SystemCoordinateDataRequest.builder()
                .requestingModule(requestingModule)
                .systemName(message.getSystemName())
                .build();
        if (systemCoordinateDataRequestRepository.find(systemCoordinateDataRequest).isEmpty()) {
            systemCoordinateDataRequestRepository.create(systemCoordinateDataRequest);
        }

        processPendingRequests();
    }

    @Override
    public void processPendingRequests() {
        systemCoordinateDataRequestRepository.findAll()
                .forEach(request -> {
                    systemRepository.findByName(request.getSystemName())
                            .ifPresent(system -> {
                                SystemCoordinatesResponse systemCoordinatesResponse = new SystemCoordinatesResponse();
                                systemCoordinatesResponse.setSystemName(system.getName());
                                systemCoordinatesResponse.setXCoordinate(system.getXCoordinate());
                                systemCoordinatesResponse.setYCoordinate(system.getYCoordinate());
                                systemCoordinatesResponse.setZCoordinate(system.getZCoordinate());

                                JsonNode jsonNode = objectMapper.valueToTree(systemCoordinatesResponse);

                                RequestDataMessage requestDataMessage = RequestDataMessage.builder()
                                        .topic(request.getRequestingModule() + "StationArrivalDistanceDataRequest")
                                        .message(jsonNode)
                                        .build();

                                requestDataMessageRepository.sendToKafka(requestDataMessage);
                                systemCoordinateDataRequestRepository.delete(request);
                            });
                });
    }
}
