package io.eddb.eddb2backend.infrastructure.persistence.util;

import io.eddb.eddb2backend.application.dto.persistence.EconomyEntity;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(EconomyEntity.class)
public class EconomyEntityIdTypeHandler extends AbstractEntityIdTypeHandler<EconomyEntity.Id> {
    public EconomyEntityIdTypeHandler() {
        super(EconomyEntity.Id.class);
    }
}
