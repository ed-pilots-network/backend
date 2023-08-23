package io.edpn.backend.trade.application.domain.filter;

import io.edpn.backend.trade.application.domain.LandingPadSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class LocateCommodityFilter {
    private String commodityDisplayName;
    private Double xCoordinate;
    private Double yCoordinate;
    private Double zCoordinate;
    private Boolean includePlanetary;
    private Boolean includeOdyssey;
    private Boolean includeFleetCarriers;
    private LandingPadSize maxLandingPadSize;
    private Long minSupply;
    private Long minDemand;
}
