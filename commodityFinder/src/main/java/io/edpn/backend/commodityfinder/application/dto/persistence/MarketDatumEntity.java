package io.edpn.backend.commodityfinder.application.dto.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarketDatumEntity {


    private CommodityEntity commodity;
    private long meanPrice;
    private long buyPrice;
    private long stock;
    private long stockBracket;
    private long sellPrice;
    private long demand;
    private long demandBracket;
    private List<String> statusFlags;
    private boolean prohibited;
}
