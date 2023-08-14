package io.edpn.backend.trade.application.domain.filter;

import io.edpn.backend.trade.application.domain.LandingPadSize;


public record LocateCommodityFilter(
        String commodityDisplayName,
        Double xCoordinate,
        Double yCoordinate,
        Double zCoordinate,
        Boolean includePlanetary,
        Boolean includeOdyssey,
        Boolean includeFleetCarriers,
        LandingPadSize maxLandingPadSize,
        Long minSupply,
        Long minDemand
) {
}
