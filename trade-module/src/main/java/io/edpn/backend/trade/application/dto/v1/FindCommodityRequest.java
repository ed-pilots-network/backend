package io.edpn.backend.trade.application.dto.v1;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class FindCommodityRequest {
    @NotNull(message = "Commodity id is mandatory")
    UUID commodityId; //Convert to String or UUID
    @NotNull(message = "Reference coordinates are mandatory")
    CoordinateDTO referenceLocation;
    Boolean includePlanetary;
    Boolean includeOdyssey;
    Boolean includeFleetCarriers;
    String landingPadSize;
    Long minSupply;
    Long minDemand;
}
