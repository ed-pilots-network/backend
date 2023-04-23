package io.eddb.eddb2backend.application.dto.persistence;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommodityMarketDatumEntity {

    private UUID id;
    private int meanPrice;
    private int buyPrice;
    private int stock;
    private int stockBracket;
    private int sellPrice;
    private int demand;
    private int demandBracket;
    private List<String> statusFlags;

}
