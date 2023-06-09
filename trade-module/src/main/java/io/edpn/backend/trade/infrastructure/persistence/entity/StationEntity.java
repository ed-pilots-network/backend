package io.edpn.backend.trade.infrastructure.persistence.entity;

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
public class StationEntity {

    private UUID id;
    private Long marketId;
    private String name;
    private Double arrivalDistance;
    private SystemEntity system;
    private Boolean planetary;
    private Boolean requireOdyssey;
    private boolean fleetCarrier;
    private String maxLandingPadSize;
    private LocalDateTime marketUpdatedAt;
    @Builder.Default
    private List<MarketDatumEntity> marketData = new ArrayList<>();
}
