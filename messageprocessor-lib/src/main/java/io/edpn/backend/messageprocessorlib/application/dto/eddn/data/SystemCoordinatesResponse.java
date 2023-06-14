package io.edpn.backend.messageprocessorlib.application.dto.eddn.data;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SystemCoordinatesResponse {
    private String systemName;
    private double xCoordinate;
    private double yCoordinate;
    private double zCoordinate;
}
