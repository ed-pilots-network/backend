package io.edpn.backend.commodityfinder.domain.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarketDatum {

    private Commodity commodity;
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
