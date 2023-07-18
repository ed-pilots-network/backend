package io.edpn.backend.trade.application.dto.v1;


import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Jacksonized
public class LocateCommodityResponse {
    String commodityName;
    Station station;
    String systemName;
    LocalDateTime pricesUpdatedAt;
    Long supply;
    Long demand;
    Long buyPrice;
    Long sellPrice;
    Double distance;

    @Value
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Jacksonized
    public static class Station {
        String name;
        Double arrivalDistance;
        @Schema(example = "SMALL", allowableValues = "UNKNOWN, SMALL, MEDIUM, LARGE")
        String maxLandingPadSize;
        Boolean fleetCarrier;
        Boolean requireOdyssey;
        Boolean planetary;
    }
}
