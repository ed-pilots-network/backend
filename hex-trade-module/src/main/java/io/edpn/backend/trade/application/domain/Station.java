package io.edpn.backend.trade.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Station {
    private UUID id;
    private Long marketId;
    private String name;
    private Double arrivalDistance;
    private System system;
    private Boolean planetary;
    private Boolean requireOdyssey;
    private Boolean fleetCarrier;
    private LandingPadSize maxLandingPadSize;
    private LocalDateTime marketUpdatedAt;
    private List<MarketDatum> marketData;
}
