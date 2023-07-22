package io.edpn.backend.trade.application.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class FindCommodityResponse {
    
    private UUID id;
    private String commodityName;
    private String displayName;
    private String type;
    private Boolean isRare;
}
