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

    private UUID id;
    private String name;
    private Long edMarketId;
    private LocalDateTime marketUpdatedAt;
    private boolean hasCommodities;
    private Collection<UUID> prohibitedCommodityIds;
    private Map<UUID, Double> economyEntityIdProportionMap;
}
