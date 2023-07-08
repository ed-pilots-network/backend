package io.edpn.backend.trade.application.mappers.v1;

import io.edpn.backend.trade.application.dto.v1.CoordinateDTO;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityResponse;
import io.edpn.backend.trade.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.domain.model.LandingPadSize;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocateCommodityDTOMapper {

    public LocateCommodityResponse map(LocateCommodity findCommodity) {
        return LocateCommodityResponse.builder()
                .pricesUpdate(findCommodity.getPricesUpdatedAt())
                .commodityName(findCommodity.getCommodity().getName())
                .station(mapStation(findCommodity.getStation()))
                .system(mapSystem(findCommodity.getSystem()))
                .build();
    }

    public LocateCommodityFilter map(LocateCommodityRequest findCommodityRequest) {
        return LocateCommodityFilter.builder()
                .commodityId(findCommodityRequest.getCommodityId())
                .xCoordinate(findCommodityRequest.getReferenceLocation().getXCoordinate())
                .yCoordinate(findCommodityRequest.getReferenceLocation().getYCoordinate())
                .zCoordinate(findCommodityRequest.getReferenceLocation().getZCoordinate())
                .includePlanetary(findCommodityRequest.getIncludePlanetary())
                .includeOdyssey(findCommodityRequest.getIncludePlanetary())
                .includeFleetCarriers(findCommodityRequest.getIncludeFleetCarriers())
                .landingPadSize(LandingPadSize.valueOf(findCommodityRequest.getLandingPadSize()))
                .minSupply(findCommodityRequest.getMinSupply())
                .minDemand(findCommodityRequest.getMinDemand())
                .build();
    }

    private LocateCommodityResponse.Station mapStation(Station station) {
        return LocateCommodityResponse.Station.builder()
                .name(station.getName())
                .arrivalDistance(station.getArrivalDistance())
                .landingPadSize(station.getMaxLandingPadSize().name())
                .planetary(station.getPlanetary())
                .requireOdyssey(station.getRequireOdyssey())
                .fleetCarrier(station.isFleetCarrier())
                .build();
    }

    //TODO: nullable coords? fix view?
    private LocateCommodityResponse.System mapSystem(System system) {
        return LocateCommodityResponse.System.builder()
                .name(system.getName())
                .coordinateDTO(
                        CoordinateDTO.builder()
                                .xCoordinate(system.getXCoordinate())
                                .yCoordinate(system.getYCoordinate())
                                .zCoordinate(system.getZCoordinate())
                                .build()
                )
                .build();
    }

}
