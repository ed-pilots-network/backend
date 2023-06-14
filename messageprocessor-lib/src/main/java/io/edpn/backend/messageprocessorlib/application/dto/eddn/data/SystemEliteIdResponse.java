package io.edpn.backend.messageprocessorlib.application.dto.eddn.data;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SystemEliteIdResponse {
    private String systemName;
    private long eliteId;
}
