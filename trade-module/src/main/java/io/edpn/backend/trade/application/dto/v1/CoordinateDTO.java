package io.edpn.backend.trade.application.dto.v1;

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
public class CoordinateDTO {
    private Double x;
    private Double y;
    private Double z;
}
