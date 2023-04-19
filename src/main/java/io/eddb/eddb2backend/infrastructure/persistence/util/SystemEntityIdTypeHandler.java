package io.eddb.eddb2backend.infrastructure.persistence.util;

import io.eddb.eddb2backend.application.dto.persistence.SystemEntity;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(SystemEntity.class)
public class SystemEntityIdTypeHandler extends AbstractEntityIdTypeHandler<SystemEntity.Id> {
    public SystemEntityIdTypeHandler() {
        super(SystemEntity.Id.class);
    }
}
