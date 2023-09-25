package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.filter.FindStationFilter;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.CleanUpObsoleteStationArrivalDistanceRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.DeleteStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.LoadAllStationArrivalDistanceRequestsPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@AllArgsConstructor
@Slf4j
public class CleanUpObsoleteStationArrivalDistanceRequestsService implements CleanUpObsoleteStationArrivalDistanceRequestsUseCase {
    public static final FindStationFilter FIND_STATION_FILTER = FindStationFilter.builder()
            .hasArrivalDistance(false)
            .build();

    private final LoadStationsByFilterPort loadStationsByFilterPort;
    private final LoadAllStationArrivalDistanceRequestsPort loadAllStationArrivalDistanceRequestsPort;
    private final DeleteStationArrivalDistanceRequestPort deleteStationArrivalDistanceRequestPort;

    @Override
    @Scheduled(cron = "0 0 4 * * *")
    public synchronized void cleanUpObsolete() {
        // find all open request in database
        List<StationDataRequest> dataRequests = loadAllStationArrivalDistanceRequestsPort.loadAll();
        // find all items that have missing info
        List<Station> missingItemsList = loadStationsByFilterPort.loadByFilter(FIND_STATION_FILTER);
        // items that are in open requests, but not in items with missing info can be removed
        dataRequests.stream()
                .filter(dataRequest -> missingItemsList.stream()
                        .noneMatch(station -> station.getName().equals(dataRequest.stationName()) && station.getSystem().getName().equals(dataRequest.systemName())))
                .forEach(dataRequest -> deleteStationArrivalDistanceRequestPort.delete(dataRequest.systemName(), dataRequest.stationName()));
    }
}
