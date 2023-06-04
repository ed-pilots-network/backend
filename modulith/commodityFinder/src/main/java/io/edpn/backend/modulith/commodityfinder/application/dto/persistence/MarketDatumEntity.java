package io.edpn.backend.modulith.commodityfinder.application.dto.persistence;

import io.edpn.backend.modulith.commodityfinder.domain.entity.Commodity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

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
