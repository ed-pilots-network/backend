package io.edpn.backend.trade.infrastructure.persistence.filter;

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
public class FindCommodityFilterPersistence {
    
    String type;
    Boolean isRare;
}
