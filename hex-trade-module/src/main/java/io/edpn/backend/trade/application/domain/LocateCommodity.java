package io.edpn.backend.trade.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class LocateCommodity {
    private LocalDateTime priceUpdatedAt;
    private ValidatedCommodity validatedCommodity;
    private Station station;
    private System system;
    private Long supply;
    private Long demand;
    private Long buyPrice;
    private Long sellPrice;
    private Double distance;
    
}
