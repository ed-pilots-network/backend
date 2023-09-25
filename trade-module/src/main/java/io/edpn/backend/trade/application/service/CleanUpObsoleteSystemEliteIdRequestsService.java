package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CleanUpObsoleteSystemEliteIdRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestsPort;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
@Slf4j
public class CleanUpObsoleteSystemEliteIdRequestsService implements CleanUpObsoleteSystemEliteIdRequestsUseCase {
    public static final FindSystemFilter FIND_SYSTEM_FILTER = FindSystemFilter.builder()
            .hasEliteId(false)
            .build();

    private final LoadSystemsByFilterPort loadSystemsByFilterPort;
    private final LoadAllSystemEliteIdRequestsPort loadAllSystemEliteIdRequestsPort;
    private final DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort;

    @Override
    @Scheduled(cron = "0 0 4 * * *")
    public synchronized void cleanUpObsolete() {
        // find all open request in database
        List<SystemDataRequest> dataRequests = loadAllSystemEliteIdRequestsPort.loadAll();
        // find all items that have missing info
        List<System> missingItemsList = loadSystemsByFilterPort.loadByFilter(FIND_SYSTEM_FILTER);
        // items that are in open requests, but not in items with missing info can be removed
        dataRequests.stream()
                .filter(dataRequest -> missingItemsList.stream()
                        .noneMatch(system -> system.getName().equals(dataRequest.systemName())))
                .forEach(dataRequest -> deleteSystemEliteIdRequestPort.delete(dataRequest.systemName()));
    }
}
