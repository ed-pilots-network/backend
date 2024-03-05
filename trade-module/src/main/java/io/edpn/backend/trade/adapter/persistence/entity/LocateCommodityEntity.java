package io.edpn.backend.trade.adapter.persistence.entity;

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

    private LocalDateTime priceUpdatedAt;
    private ValidatedCommodityEntity validatedCommodity;
    private StationEntity station;
    private Long supply;
    private Long demand;
    private Long buyPrice;
    private Long sellPrice;
    private Double distance;
}
