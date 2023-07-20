package io.edpn.backend.exploration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class SystemCoordinateDataRequest {

    private String systemName;
    private String requestingModule;
}
