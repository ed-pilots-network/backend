package io.edpn.backend.trade.application.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class CoordinateDTO {
    private Double xCoordinate;
    private Double yCoordinate;
    private Double zCoordinate;
}
