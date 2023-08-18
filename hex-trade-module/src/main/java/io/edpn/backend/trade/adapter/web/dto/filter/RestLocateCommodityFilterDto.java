package io.edpn.backend.trade.adapter.web.dto.filter;

import io.edpn.backend.trade.application.dto.web.filter.LocateCommodityFilterDto;

public record RestLocateCommodityFilterDto(
        String commodityDisplayName,
        Double xCoordinate,
        Double yCoordinate,
        Double zCoordinate,
        Boolean includePlanetary,
        Boolean includeOdyssey,
        Boolean includeFleetCarriers,
        String maxLandingPadSize,
        Long minSupply,
        Long minDemand) implements LocateCommodityFilterDto {
}
