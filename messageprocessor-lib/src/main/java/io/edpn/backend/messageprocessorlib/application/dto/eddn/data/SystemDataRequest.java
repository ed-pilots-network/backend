package io.edpn.backend.messageprocessorlib.application.dto.eddn.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SystemDataRequest {

    @JsonProperty("systemName")
    private String systemName;
}
