package io.edpn.backend.trade.application.domain.filter;

import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
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
//TODO: rework Route/station options
public class FindSingleHopFilter {
    private System buyFromSystem;
    private System sellToSystem;
    private Station buyFromStation;
    private Station sellToStation;
    
    //Route options
    private ValidatedCommodity validatedCommodity;
    private Double maxHopDistance;
    private Integer maxCargoCapacity;
    private Integer availableCredits;
    private Integer maxPriceAgeInDays;
    private Long minSupply;
    private Long minDemand;
    
    //station options
    private LandingPadSize maxLandingPadSize;
    private Long maxDistanceFromStarInLs;
}