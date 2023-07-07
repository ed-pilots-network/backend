package io.edpn.backend.trade.application.dto;

import lombok.*;
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
