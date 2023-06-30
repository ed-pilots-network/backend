package io.edpn.backend.trade.application.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CoordinateDTO {
    double xCoordinate;
    double yCoordinate;
    double zCoordinate;
}
