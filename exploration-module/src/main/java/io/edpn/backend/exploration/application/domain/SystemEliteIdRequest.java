package io.edpn.backend.exploration.application.domain;

import io.edpn.backend.util.Module;
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
public class SystemEliteIdRequest {
    private String systemName;
    private Module requestingModule;
}
