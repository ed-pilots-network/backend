package io.edpn.backend.trade.application.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class FindCommodityResponse {
    
    private String commodityName;
    private String displayName;
    private String type;
    private Boolean isRare;
}
