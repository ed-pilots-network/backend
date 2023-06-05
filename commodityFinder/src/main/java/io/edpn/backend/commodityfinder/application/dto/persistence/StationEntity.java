package io.edpn.backend.commodityfinder.application.dto.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
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
    private SystemEntity system;
    private boolean planetary;
    private boolean requireOdyssey;
    private boolean fleetCarrier;
    private String maxLandingPadSize;
    private LocalDateTime marketUpdatedAt;
    private List<MarketDatumEntity> marketData;
}
