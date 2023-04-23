package io.eddb.eddb2backend.application.dto.persistence;

import lombok.*;

import java.time.LocalDateTime;
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
    private UUID marketDatumId;

}
