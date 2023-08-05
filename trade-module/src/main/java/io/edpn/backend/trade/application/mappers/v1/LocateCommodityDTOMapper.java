package io.edpn.backend.trade.application.mappers.v1;

import io.edpn.backend.trade.application.dto.v1.LocateCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityResponse;
import io.edpn.backend.trade.domain.filter.v1.LocateCommodityFilter;
import io.edpn.backend.trade.domain.model.LandingPadSize;
import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.domain.model.Station;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class LocateCommodityDTOMapper {

    public LocateCommodityResponse map(LocateCommodity locateCommodity) {
        return LocateCommodityResponse.builder()
                .pricesUpdatedAt(locateCommodity.getPricesUpdatedAt())
                .commodityDisplayName(locateCommodity.getValidatedCommodity().getDisplayName())
                .station(mapStation(locateCommodity.getStation()))
                .systemName(locateCommodity.getSystem().getName())
                .supply(locateCommodity.getSupply())
                .demand(locateCommodity.getDemand())
                .buyPrice(locateCommodity.getBuyPrice())
                .sellPrice(locateCommodity.getSellPrice())
                .distance(locateCommodity.getDistance())
                .build();
    }

    public LocateCommodityFilter map(LocateCommodityRequest locateCommodityRequest) {
        return LocateCommodityFilter.builder()
                .commodityDisplayName(locateCommodityRequest.getCommodityDisplayName())
                .xCoordinate(locateCommodityRequest.getX())
                .yCoordinate(locateCommodityRequest.getY())
                .zCoordinate(locateCommodityRequest.getZ())
                .includePlanetary(locateCommodityRequest.getIncludePlanetary())
                .includeOdyssey(locateCommodityRequest.getIncludePlanetary())
                .includeFleetCarriers(locateCommodityRequest.getIncludeFleetCarriers())
                .maxLandingPadSize(Optional.ofNullable(locateCommodityRequest.getMaxLandingPadSize()).map(LandingPadSize::valueOf).orElse(LandingPadSize.UNKNOWN))
                .minSupply(locateCommodityRequest.getMinSupply())
                .minDemand(locateCommodityRequest.getMinDemand())
                .build();
    }

    private LocateCommodityResponse.Station mapStation(Station station) {
        return LocateCommodityResponse.Station.builder()
                .name(station.getName())
                .arrivalDistance(station.getArrivalDistance())
                .maxLandingPadSize(station.getMaxLandingPadSize().name())
                .planetary(station.getPlanetary())
                .requireOdyssey(station.getRequireOdyssey())
                .fleetCarrier(station.getFleetCarrier())
                .build();
    }

}
