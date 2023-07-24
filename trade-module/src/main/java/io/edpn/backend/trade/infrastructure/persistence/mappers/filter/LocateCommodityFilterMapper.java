package io.edpn.backend.trade.infrastructure.persistence.mappers.filter;

import io.edpn.backend.trade.domain.filter.v1.LocateCommodityFilter;
import io.edpn.backend.trade.infrastructure.persistence.filter.LocateCommodityFilterPersistence;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocateCommodityFilterMapper {

    public LocateCommodityFilterPersistence map(LocateCommodityFilter filter) {
        return LocateCommodityFilterPersistence.builder()
                .commodityDisplayName(filter.getCommodityDisplayName())
                .xCoordinate(filter.getXCoordinate())
                .yCoordinate(filter.getYCoordinate())
                .zCoordinate(filter.getZCoordinate())
                .includePlanetary(filter.getIncludePlanetary())
                .includeOdyssey(filter.getIncludeOdyssey())
                .includeFleetCarriers(filter.getIncludeFleetCarriers())
                .maxLandingPadSize(filter.getMaxLandingPadSize().name())
                .minSupply(filter.getMinSupply())
                .minDemand(filter.getMinDemand())
                .build();
    }
}
