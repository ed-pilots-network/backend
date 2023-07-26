package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.service.RequestDataService;
import io.edpn.backend.trade.domain.service.SendDataRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class RequestSystemEliteIdService implements RequestDataService<System> {

    public static final String TOPIC = "systemEliteIdDataRequest";
    public static final String REQUESTING_MODULE = "trade";
    private final SendDataRequestService<SystemDataRequest> systemDataRequestSendDataRequestService;

    @Override
    public boolean isApplicable(System system) {
        return Objects.isNull(system.getEliteId());
    }

    @Override
    public void request(System system) {
        SystemDataRequest systemDataRequest = new SystemDataRequest();
        systemDataRequest.setSystemName(system.getName());
        systemDataRequest.setRequestingModule(REQUESTING_MODULE);

        systemDataRequestSendDataRequestService.send(systemDataRequest, TOPIC);
    }
}
