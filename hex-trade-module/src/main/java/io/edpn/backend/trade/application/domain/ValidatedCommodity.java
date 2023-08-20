package io.edpn.backend.trade.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class ValidatedCommodity {
    private String commodityName;
    private String displayName;
    private CommodityType type;
    private Boolean isRare;
}
