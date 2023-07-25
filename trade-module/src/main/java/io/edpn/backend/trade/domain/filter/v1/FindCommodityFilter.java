package io.edpn.backend.trade.domain.filter.v1;

import io.edpn.backend.trade.domain.model.CommodityType;
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
public class FindCommodityFilter {
    
    CommodityType type;
    Boolean isRare;
}
