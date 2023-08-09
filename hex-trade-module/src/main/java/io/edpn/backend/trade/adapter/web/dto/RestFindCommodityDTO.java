package io.edpn.backend.trade.adapter.web.dto;

import io.edpn.backend.trade.application.dto.FindCommodityDTO;
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
public class RestFindCommodityDTO implements FindCommodityDTO {
    private String type;
    private Boolean isRare;
}
