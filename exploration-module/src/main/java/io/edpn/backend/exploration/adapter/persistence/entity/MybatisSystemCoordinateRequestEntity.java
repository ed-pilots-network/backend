package io.edpn.backend.exploration.adapter.persistence.entity;

import io.edpn.backend.exploration.application.dto.SystemCoordinateRequestEntity;
import io.edpn.backend.util.Module;
import lombok.AllArgsConstructor;
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
public class MybatisSystemCoordinateRequestEntity implements SystemCoordinateRequestEntity {
    private String systemName;
    private Module requestingModule;
}
