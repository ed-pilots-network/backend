package io.edpn.backend.trade.adapter.persistence.entity;

import io.edpn.backend.trade.application.dto.persistence.entity.StationEntity;
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
public class MybatisStationEntity implements StationEntity {
    
    private UUID id;
    private Long marketId;
    private String name;
    private Double arrivalDistance;
    private MybatisSystemEntity system;
    private Boolean planetary;
    private Boolean requireOdyssey;
    private Boolean fleetCarrier;
    private String maxLandingPadSize;
    private LocalDateTime marketUpdatedAt;
    @Builder.Default
    private List<MybatisMarketDatumEntity> marketData = new ArrayList<>();
}
