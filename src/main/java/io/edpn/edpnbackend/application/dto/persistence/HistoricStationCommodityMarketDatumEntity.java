package io.edpn.edpnbackend.application.dto.persistence;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricStationCommodityMarketDatumEntity {

    private UUID id;
    private UUID stationId;
    private UUID commodityId;
    private LocalDateTime timestamp;
    private int meanPrice;
    private int buyPrice;
    private int stock;
    private int stockBracket;
    private int sellPrice;
    private int demand;
    private int demandBracket;
    private List<String> statusFlags;

}
