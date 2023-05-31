package io.edpn.backend.modulith.commodityfinder.application.dto.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarketDatumEntity {

    private Id id;
    private long meanPrice;
    private long buyPrice;
    private long stock;
    private long stockBracket;
    private long sellPrice;
    private long demand;
    private long demandBracket;
    private List<String> statusFlags;
    private boolean prohibited;


    @Value
    public static class Id {
        StationEntity station;
        CommodityEntity commodityId;
    }
}
