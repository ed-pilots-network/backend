package io.eddb.eddb2backend.infrastructure.persistence.util;

import io.eddb.eddb2backend.application.dto.persistence.StationEntity;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(StationEntity.class)
public class StationEntityIdTypeHandler extends AbstractEntityIdTypeHandler<StationEntity.Id> {
    public StationEntityIdTypeHandler() {
        super(StationEntity.Id.class);
    }
}
