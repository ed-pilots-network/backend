package io.edpn.backend.exploration.adapter.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SystemCoordinateRequestEntity implements io.edpn.backend.exploration.application.dto.SystemCoordinateRequestEntity {
    private String systemName;
    private String requestingModule;
}
