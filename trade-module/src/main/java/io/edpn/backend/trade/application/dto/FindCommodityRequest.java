package io.edpn.backend.trade.application.dto;

import io.edpn.backend.trade.domain.model.Commodity;
import io.edpn.backend.trade.domain.model.LandingPadSize;
import jakarta.validation.constraints.NotNull;
import lombok.*;
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
