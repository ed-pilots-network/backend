package io.edpn.backend.exploration.infrastructure.persistence.entity;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemCoordinateDataRequestEntity {

    private String systemName;
    private String requestingModule;
}
