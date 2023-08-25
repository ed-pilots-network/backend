package io.edpn.backend.trade.adapter.persistence.filter;

import io.edpn.backend.trade.application.dto.persistence.filter.PersistenceFindSystemFilter;
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
public class MybatisFindSystemFilter implements PersistenceFindSystemFilter {

    private String name;
    private Boolean hasEliteId;
    private Boolean hasCoordinates;
}