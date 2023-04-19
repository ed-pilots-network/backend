package io.eddb.eddb2backend.application.dto.persistence;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricStationCommodityEntity {

    private Id id;
    private int meanPrice;
    private int buyPrice;
    private int stock;
    private int stockBracket;
    private int sellPrice;
    private int demand;
    private int demandBracket;
    private List<String> statusFlags;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id {
        StationEntity.Id stationId;
        CommodityEntity.Id commodityId;
        LocalDateTime timestamp;
    }

}
