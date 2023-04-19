package io.eddb.eddb2backend.application.dto.persistence;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
@Builder
public class StationCommodityEntity {

    private Id id;
    private int meanPrice;
    private int buyPrice;
    private int stock;
    private int stockBracket;
    private int sellPrice;
    private int demand;
    private int demandBracket;
    private List<String> statusFlags;

    @Value
    public static class Id {
        StationEntity.Id systemId;
        CommodityEntity.Id commodityId;
    }
}
