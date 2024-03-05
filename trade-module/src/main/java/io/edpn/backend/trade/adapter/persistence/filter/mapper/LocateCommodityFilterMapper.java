package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.LocateCommodityFilter;

public class LocateCommodityFilterMapper {

    public LocateCommodityFilter map(io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter locateCommodityFilter) {
        return LocateCommodityFilter.builder()
                .commodityDisplayName(locateCommodityFilter.getCommodityDisplayName())
                .xCoordinate(locateCommodityFilter.getXCoordinate())
                .yCoordinate(locateCommodityFilter.getYCoordinate())
                .zCoordinate(locateCommodityFilter.getZCoordinate())
                .includePlanetary(locateCommodityFilter.getIncludePlanetary())
                .includeOdyssey(locateCommodityFilter.getIncludeOdyssey())
                .includeFleetCarriers(locateCommodityFilter.getIncludeFleetCarriers())
                .maxLandingPadSize(String.valueOf(locateCommodityFilter.getMaxLandingPadSize()))
                .minSupply(locateCommodityFilter.getMinSupply())
                .minDemand(locateCommodityFilter.getMinDemand())
                .build();
    }
}
