package io.edpn.backend.trade.domain.filter.v1;

import io.edpn.backend.trade.domain.model.LandingPadSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
