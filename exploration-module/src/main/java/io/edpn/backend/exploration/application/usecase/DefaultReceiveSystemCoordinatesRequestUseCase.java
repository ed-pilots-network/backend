package io.edpn.backend.exploration.application.usecase;

import io.edpn.backend.exploration.domain.model.System;
import io.edpn.backend.exploration.domain.model.SystemCoordinateDataRequest;
import io.edpn.backend.exploration.domain.repository.SystemCoordinateDataRequestRepository;
import io.edpn.backend.exploration.domain.repository.SystemRepository;
import io.edpn.backend.exploration.domain.service.SendDataResponseService;
import io.edpn.backend.exploration.domain.usecase.ReceiveDataRequestUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class DefaultReceiveSystemCoordinatesRequestUseCase implements ReceiveDataRequestUseCase<SystemDataRequest> {

    private final SystemRepository systemRepository;
    private final SystemCoordinateDataRequestRepository systemCoordinateDataRequestRepository;
    private final SendDataResponseService<SystemCoordinatesResponse> SendSystemCoordinateDataResponseService;

    @Override
    public void receive(SystemDataRequest message) {
        CompletableFuture.supplyAsync(() -> systemRepository.findByName(message.getSystemName()))
                .thenAcceptAsync(optionalSystem ->
                        optionalSystem.ifPresentOrElse(
                                system -> SendSystemCoordinateDataResponseService.send(createSystemCoordinatesResponse(system), message.getRequestingModule()),
                                () -> createSystemCoordinateDataRequest(message)
                        )
                );
    }

    private void createSystemCoordinateDataRequest(SystemDataRequest message) {
        systemCoordinateDataRequestRepository.create(
                SystemCoordinateDataRequest.builder()
                        .requestingModule(message.getRequestingModule())
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
