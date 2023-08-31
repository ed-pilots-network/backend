package io.edpn.backend.trade.adapter.web.dto.filter;

import io.edpn.backend.trade.application.dto.web.filter.LocateCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.filter.PageFilterDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LocateCommodityFilterDto")
public record RestLocateCommodityFilterDto(
        String commodityDisplayName,
        Double xCoordinate,
        Double yCoordinate,
        Double zCoordinate,
        Boolean includePlanetary,
        Boolean includeOdyssey,
        Boolean includeFleetCarriers,
        String shipSize,
        Long minSupply,
        Long minDemand,
        RestPageFilterDto page
) implements LocateCommodityFilterDto {
}
