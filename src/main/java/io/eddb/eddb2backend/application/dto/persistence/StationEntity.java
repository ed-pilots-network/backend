package io.eddb.eddb2backend.application.dto.persistence;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class StationEntity {

    private Id id;
    private String name;
    private Long edMarketId;
    private LocalDateTime marketUpdatedAt;
    private boolean hasCommodities;
    private Collection<CommodityEntity.Id> prohibitedCommodityIds;
    private Map<EconomyEntity.Id, Double> economyEntityIdProportionMap;
    private Collection<StationCommodityEntity.Id> commodities;

    @Value(staticConstructor = "of")
    public static class Id {
        UUID value;
    }
}
