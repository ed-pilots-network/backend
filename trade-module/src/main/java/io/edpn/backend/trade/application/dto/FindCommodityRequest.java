package io.edpn.backend.trade.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.edpn.backend.trade.domain.model.Commodity;
import io.edpn.backend.trade.domain.model.LandingPadSize;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Data
@Builder
@Jacksonized
public class FindCommodityRequest {
    @NotNull(message = "Commodity is mandatory")
    Commodity commodity; //Convert to String or UUID
//    @NotNull(message = "Reference coordinates are mandatory")
    CoordinateDTO referenceLocation;
    boolean includePlanetary;
    boolean includeOdyssey;
    boolean includeFleetCarriers;
    LandingPadSize maxLandingPadSize;
    long minSupply;
    boolean isBuying;
}
