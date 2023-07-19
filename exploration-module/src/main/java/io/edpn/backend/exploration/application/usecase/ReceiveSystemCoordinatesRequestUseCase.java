package io.edpn.backend.exploration.application.usecase;

import io.edpn.backend.exploration.domain.usecase.ReceiveDataRequestUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ReceiveSystemCoordinatesRequestUseCase implements ReceiveDataRequestUseCase<SystemDataRequest> {
    @Override
    public void receive(SystemDataRequest message, String sendingModule) {
        //TODO
    }
}
