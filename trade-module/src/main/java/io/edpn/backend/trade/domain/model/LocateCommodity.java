package io.edpn.backend.trade.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocateCommodity {
    private LocalDateTime pricesUpdatedAt;
    private Commodity commodity;
    private Station station;
    private System system;
    private Long supply;
    private Long demand;
}
