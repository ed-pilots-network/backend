package io.edpn.backend.trade.infrastructure.persistence.entity;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidatedCommodityEntity {
    
    private UUID id;
    private String commodityName;
    private String displayName;
    private String type;
    private Boolean isRare;
}
