package io.eddb.eddb2backend.infrastructure.persistence.util;

import io.eddb.eddb2backend.application.dto.persistence.StationTypeEntity;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(StationTypeEntity.class)
public class StationTypeEntityIdTypeHandler extends AbstractEntityIdTypeHandler<StationTypeEntity.Id> {
    public StationTypeEntityIdTypeHandler() {
        super(StationTypeEntity.Id.class);
    }
}
