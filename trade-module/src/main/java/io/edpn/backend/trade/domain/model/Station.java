package io.edpn.backend.trade.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
    @Builder.Default
    private LandingPadSize maxLandingPadSize = LandingPadSize.UNKNOWN;
    private LocalDateTime marketUpdatedAt;
    @Builder.Default
    private List<MarketDatum> marketData = new ArrayList<>();
}
