package io.edpn.backend.trade.adapter.web.dto;

import io.edpn.backend.trade.application.dto.ValidatedCommodityDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class RestValidatedCommodityDTO implements ValidatedCommodityDTO {
    
    private String commodityName;
    private String displayName;
    private String type;
    private Boolean isRare;
}
