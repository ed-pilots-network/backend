package io.edpn.backend.messageprocessorlib.application.dto.eddn.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StationArrivalDistanceResponse {
    @JsonProperty("stationName")
    private String stationName;
    @JsonProperty("systemName")
    private String systemName;
    @JsonProperty("arrivalDistance")
    private double arrivalDistance;
}
