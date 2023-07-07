package io.edpn.backend.trade.application.mappers.v1;

import io.edpn.backend.trade.application.dto.v1.CoordinateDTO;
import io.edpn.backend.trade.application.dto.v1.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.FindCommodityResponse;
import io.edpn.backend.trade.domain.model.FindCommodity;
import io.edpn.backend.trade.domain.model.FindCommodityFilter;
import io.edpn.backend.trade.domain.model.LandingPadSize;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindCommodityDTOMapper {

    public FindCommodityResponse map(FindCommodity findCommodity) {
        return FindCommodityResponse.builder()
                .pricesUpdate(findCommodity.getPricesUpdatedAt())
                .commodityName(findCommodity.getCommodity().getName())
                .station(mapStation(findCommodity.getStation()))
                .system(mapSystem(findCommodity.getSystem()))
                .build();
    }

    public FindCommodityFilter map(FindCommodityRequest findCommodityRequest) {
        return FindCommodityFilter.builder()
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

    private FindCommodityResponse.Station mapStation(Station station) {
        return FindCommodityResponse.Station.builder()
                .name(station.getName())
                .arrivalDistance(station.getArrivalDistance())
                .landingPadSize(station.getMaxLandingPadSize().name())
                .planetary(station.getPlanetary())
                .requireOdyssey(station.getRequireOdyssey())
                .fleetCarrier(station.isFleetCarrier())
                .build();
    }

    //TODO: nullable coords? fix view?
    private FindCommodityResponse.System mapSystem(System system) {
        return FindCommodityResponse.System.builder()
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
