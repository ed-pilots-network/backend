package io.edpn.backend.trade.application.dto.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
public class LocateCommodityRequest {
    @NotNull(message = "Commodity id is mandatory")
    UUID commodityId;
    @NotNull(message = "Reference coordinates are mandatory")
    @Valid
    CoordinateDTO referenceLocation;
    @Schema(example = "SMALL", allowableValues = "UNKNOWN, SMALL, MEDIUM, LARGE")
    String maxLandingPadSize;
    Long minSupply;
    Long minDemand;
    Boolean includeFleetCarriers;
    Boolean includeOdyssey;
    Boolean includePlanetary;

}
