package io.edpn.backend.trade.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class LocateCommodity {
    private LocalDateTime pricesUpdatedAt;
    private ValidatedCommodity validatedCommodity;
    private Station station;
    private System system;
    private Long supply;
    private Long demand;
    private Long buyPrice;
    private Long sellPrice;
    private Double distance;
}
