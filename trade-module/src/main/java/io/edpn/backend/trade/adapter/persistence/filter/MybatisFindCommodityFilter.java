package io.edpn.backend.trade.adapter.persistence.filter;

import io.edpn.backend.trade.application.dto.persistence.filter.PersistenceFindCommodityFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class MybatisFindCommodityFilter implements PersistenceFindCommodityFilter {
    
    private String type;
    private Boolean isRare;
}
