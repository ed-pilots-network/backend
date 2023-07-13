package io.edpn.backend.trade.application.dto.v1;


import java.time.LocalDateTime;
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
    LocalDateTime pricesUpdatedAt;
    String commodityName;
    Station station;
    String systemName;
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
        String maxLandingPadSize;
        Boolean planetary;
        Boolean requireOdyssey;
        Boolean fleetCarrier;
    }
}
