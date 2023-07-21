package io.edpn.backend.trade.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class ValidatedCommodity {
    
    private UUID id;
    private String commodityName;
    private String displayName;
    private CommodityType type;
    private Boolean isRare;
}
