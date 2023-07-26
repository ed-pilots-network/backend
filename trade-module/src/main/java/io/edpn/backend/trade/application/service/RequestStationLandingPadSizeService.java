package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.domain.model.LandingPadSize;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.service.RequestDataService;
import io.edpn.backend.trade.domain.service.SendDataRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class RequestStationLandingPadSizeService implements RequestDataService<Station> {

    public static final String TOPIC = "stationMaxLandingPadSizeDataRequest";
    public static final String REQUESTING_MODULE = "trade";
    private final SendDataRequestService<StationDataRequest> stationDataRequestSendDataRequestService;

    @Override
    public boolean isApplicable(Station station) {
        return Objects.isNull(station.getMaxLandingPadSize()) || LandingPadSize.UNKNOWN == station.getMaxLandingPadSize();
    }

    @Override
    public void request(Station station) {
        StationDataRequest stationDataRequest = new StationDataRequest();
        stationDataRequest.setStationName(station.getName());
        stationDataRequest.setSystemName(station.getSystem().getName());
        stationDataRequest.setRequestingModule(REQUESTING_MODULE);

        stationDataRequestSendDataRequestService.send(stationDataRequest, TOPIC);
    }
}
