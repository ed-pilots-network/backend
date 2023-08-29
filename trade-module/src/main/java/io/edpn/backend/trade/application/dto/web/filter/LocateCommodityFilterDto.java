package io.edpn.backend.trade.application.dto.web.filter;

public interface LocateCommodityFilterDto {

    String commodityDisplayName();

    Double xCoordinate();

    Double yCoordinate();

    Double zCoordinate();

    Boolean includePlanetary();

    Boolean includeOdyssey();

    Boolean includeFleetCarriers();

    String maxLandingPadSize();

    Long minSupply();

    Long minDemand();

    PageFilterDto page();
}
