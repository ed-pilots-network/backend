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
    Double xCoordinate;
    Double yCoordinate;
    Double zCoordinate;
}
