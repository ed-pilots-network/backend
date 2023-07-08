package io.edpn.backend.trade.infrastructure.persistence.mappers.filter;

import io.edpn.backend.trade.domain.filter.FindCommodityFilter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindCommodityFilterMapper {

    public io.edpn.backend.trade.infrastructure.persistence.filter.FindCommodityFilter map(FindCommodityFilter filter) {
        return io.edpn.backend.trade.infrastructure.persistence.filter.FindCommodityFilter.builder()
                .commodityId(filter.getCommodityId())
                .xCoordinate(filter.getXCoordinate())
                .yCoordinate(filter.getYCoordinate())
                .zCoordinate(filter.getZCoordinate())
                .includePlanetary(filter.getIncludePlanetary())
                .includeOdyssey(filter.getIncludeOdyssey())
                .includeFleetCarriers(filter.getIncludeFleetCarriers())
                .landingPadSize(filter.getLandingPadSize())
                .minSupply(filter.getMinSupply())
                .minDemand(filter.getMinDemand())
                .build();
    }
}
