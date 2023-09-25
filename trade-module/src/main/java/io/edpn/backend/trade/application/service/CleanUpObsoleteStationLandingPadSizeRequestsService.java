package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.filter.FindStationFilter;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.CleanUpObsoleteStationLandingPadSizeRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.DeleteStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.LoadAllStationLandingPadSizeRequestsPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@AllArgsConstructor
@Slf4j
public class CleanUpObsoleteStationLandingPadSizeRequestsService implements CleanUpObsoleteStationLandingPadSizeRequestsUseCase {
    public static final FindStationFilter FIND_STATION_FILTER = FindStationFilter.builder()
            .hasLandingPadSize(false)
            .build();

    private final LoadStationsByFilterPort loadStationsByFilterPort;
    private final LoadAllStationLandingPadSizeRequestsPort loadAllStationLandingPadSizeRequestsPort;
    private final DeleteStationLandingPadSizeRequestPort deleteStationLandingPadSizeRequestPort;

    @Override
    @Scheduled(cron = "0 0 4 * * *")
    public synchronized void cleanUpObsolete() {
        // find all open request in database
        List<StationDataRequest> dataRequests = loadAllStationLandingPadSizeRequestsPort.loadAll();
        // find all items that have missing info
        List<Station> missingItemsList = loadStationsByFilterPort.loadByFilter(FIND_STATION_FILTER);
        // items that are in open requests, but not in items with missing info can be removed
        dataRequests.stream()
                .filter(dataRequest -> missingItemsList.stream()
                        .noneMatch(station -> station.getName().equals(dataRequest.stationName()) && station.getSystem().getName().equals(dataRequest.systemName())))
                .forEach(dataRequest -> deleteStationLandingPadSizeRequestPort.delete(dataRequest.systemName(), dataRequest.stationName()));
        log.info("cleaned obsolete StationLandingPadSizeRequests");
    }
}
