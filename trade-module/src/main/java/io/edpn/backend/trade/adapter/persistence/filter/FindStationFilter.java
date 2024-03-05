package io.edpn.backend.trade.adapter.persistence.filter;

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
public class FindStationFilter {

    private Boolean hasRequiredOdyssey;
    private Boolean hasLandingPadSize;
    private Boolean hasPlanetary;
    private Boolean hasArrivalDistance;
}
