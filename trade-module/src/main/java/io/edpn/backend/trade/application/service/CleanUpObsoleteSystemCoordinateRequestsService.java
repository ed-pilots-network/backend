package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CleanUpObsoleteSystemCoordinateRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestsPort;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
@Slf4j
public class CleanUpObsoleteSystemCoordinateRequestsService implements CleanUpObsoleteSystemCoordinateRequestsUseCase {
    public static final FindSystemFilter FIND_SYSTEM_FILTER = FindSystemFilter.builder()
            .hasCoordinates(false)
            .build();

    private final LoadSystemsByFilterPort loadSystemsByFilterPort;
    private final LoadAllSystemCoordinateRequestsPort loadAllSystemCoordinateRequestsPort;
    private final DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;

    @Override
    @Scheduled(cron = "0 0 4 * * *")
    public synchronized void cleanUpObsolete() {
        // find all open request in database
        List<SystemDataRequest> dataRequests = loadAllSystemCoordinateRequestsPort.loadAll();
        // find all items that have missing info
        List<System> missingItemsList = loadSystemsByFilterPort.loadByFilter(FIND_SYSTEM_FILTER);
        // items that are in open requests, but not in items with missing info can be removed
        dataRequests.stream()
                .filter(dataRequest -> missingItemsList.stream()
                        .noneMatch(system -> system.getName().equals(dataRequest.systemName())))
                .forEach(dataRequest -> deleteSystemCoordinateRequestPort.delete(dataRequest.systemName()));
    }
}
