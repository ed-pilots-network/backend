package io.edpn.backend.messageprocessorlib.application.dto.eddn.data;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StationMaxLandingPadSizeResponse {
    private String stationName;
    private String systemName;
    private String maxLandingPadSize;
}
