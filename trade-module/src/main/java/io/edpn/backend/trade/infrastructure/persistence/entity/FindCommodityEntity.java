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
public class FindCommodityEntity {
    private LocalDateTime pricesUpdatedAt;
    private CommodityEntity commodity;
    private StationEntity station;
    private SystemEntity system;
}
