package io.edpn.backend.trade.domain.model;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCommodity {
    private LocalDateTime pricesUpdatedAt;
    private Commodity commodity;
    private Station station;
    private System system;
}
