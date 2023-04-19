package io.eddb.eddb2backend.application.dto.persistence;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StationEntity {

    private Id id;
    private String name;
    private Long edMarketId;
    private LocalDateTime marketUpdatedAt;
    private boolean hasCommodities;
    private Collection<CommodityEntity.Id> prohibitedCommodityIds;
    private Map<EconomyEntity.Id, Double> economyEntityIdProportionMap;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id {
        UUID value;
    }
}
