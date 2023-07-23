package io.edpn.backend.trade.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
