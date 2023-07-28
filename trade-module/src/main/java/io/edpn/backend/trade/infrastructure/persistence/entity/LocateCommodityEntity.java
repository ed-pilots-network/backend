package io.edpn.backend.trade.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocateCommodityEntity {
    private LocalDateTime pricesUpdatedAt;
    private ValidatedCommodityEntity validatedCommodity;
    private StationEntity station;
    private SystemEntity system;
    private Long supply;
    private Long demand;
    private Long buyPrice;
    private Long sellPrice;
    private Double distance;
}
