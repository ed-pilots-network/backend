package io.edpn.backend.trade.infrastructure.persistence.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocateCommodityFilterPersistence {
    private UUID commodityId;
    private Double xCoordinate;
    private Double yCoordinate;
    private Double zCoordinate;
    private Boolean includePlanetary;
    private Boolean includeOdyssey;
    private Boolean includeFleetCarriers;
    private String maxLandingPadSize;
    private Long minSupply;
    private Long minDemand;
}