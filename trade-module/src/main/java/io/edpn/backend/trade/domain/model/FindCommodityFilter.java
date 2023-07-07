package io.edpn.backend.trade.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCommodityFilter {
    private UUID commodityId;
    private Double xCoordinate;
    private Double yCoordinate;
    private Double zCoordinate;
    private Boolean includePlanetary;
    private Boolean includeOdyssey;
    private Boolean includeFleetCarriers;
    private LandingPadSize landingPadSize;
    private Long minSupply;
    private Long minDemand;
}
