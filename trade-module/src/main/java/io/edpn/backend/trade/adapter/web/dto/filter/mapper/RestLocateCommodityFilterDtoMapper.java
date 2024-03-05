package io.edpn.backend.trade.adapter.web.dto.filter.mapper;

import io.edpn.backend.trade.adapter.web.dto.filter.RestLocateCommodityFilterDto;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;

public class RestLocateCommodityFilterDtoMapper {
    public LocateCommodityFilter map(RestLocateCommodityFilterDto restLocateCommodityFilterDto) {
        return new LocateCommodityFilter(
                restLocateCommodityFilterDto.commodityDisplayName(),
                restLocateCommodityFilterDto.xCoordinate(),
                restLocateCommodityFilterDto.yCoordinate(),
                restLocateCommodityFilterDto.zCoordinate(),
                restLocateCommodityFilterDto.includePlanetary(),
                restLocateCommodityFilterDto.includeOdyssey(),
                restLocateCommodityFilterDto.includeFleetCarriers(),
                LandingPadSize.valueOf(restLocateCommodityFilterDto.maxLandingPadSize()),
                restLocateCommodityFilterDto.minSupply(),
                restLocateCommodityFilterDto.minDemand()
        );
    }
}
