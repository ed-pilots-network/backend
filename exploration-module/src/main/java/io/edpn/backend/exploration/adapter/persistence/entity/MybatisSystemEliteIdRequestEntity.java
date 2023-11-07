package io.edpn.backend.exploration.adapter.persistence.entity;

import io.edpn.backend.exploration.application.dto.persistence.entity.SystemEliteIdRequestEntity;
import io.edpn.backend.util.Module;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class MybatisSystemEliteIdRequestEntity implements SystemEliteIdRequestEntity {
    private String systemName;
    private Module requestingModule;
}
