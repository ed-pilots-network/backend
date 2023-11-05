package io.edpn.backend.exploration.adapter.persistence.entity;

import io.edpn.backend.exploration.application.dto.persistence.entity.SystemCoordinateRequestEntity;
import io.edpn.backend.util.Module;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class MybatisSystemCoordinateRequestEntity implements SystemCoordinateRequestEntity {
    private String systemName;
    private Module requestingModule;
}
